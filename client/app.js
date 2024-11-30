let map;
let markers = [];
const API_BASE_URL = 'http://localhost:8081/api';
let routeLine;
let currentInfoWindow = null;

// 사용 가능한 태그 목록과 서버 태그 매핑
const tagMapping = {
    "역사": "HISTORICAL",
    "문화": "CULTURAL",
    "자연": "NATURAL",
    "현대": "MODERN",
    "쇼핑": "SHOPPING",
    "음식": "CULINARY",
    "전통": "TRADITIONAL",
    "종교": "RELIGIOUS",
    "예술": "ARTISTIC",
    "교육": "EDUCATIONAL",
    "계절": "SEASONAL",
    "휴식": "RELAXATION",
    "가족": "FAMILY",
    "스포츠": "SPORTS",
    "엔터테인먼트": "ENTERTAINMENT"
};

const availableTags = Object.keys(tagMapping);

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    const searchButton = document.getElementById('searchButton');
    const countrySelect = document.getElementById('countrySelect');
    const errorDiv = document.getElementById('error');

    searchButton.addEventListener('click', async () => {
        const selectedCountry = countrySelect.value;
        const selectedTags = Array.from(document.querySelectorAll('input[type="checkbox"]:checked'))
            .map(checkbox => checkbox.value);

        if (selectedTags.length < 2 || selectedTags.length > 5) {
            errorDiv.textContent = '태그를 2-5개 선택해주세요.';
            errorDiv.style.display = 'block';
            return;
        }

        errorDiv.style.display = 'none';
        showLoading();

        try {
            // 경로 최적화 API 호출
            const response = await fetch(`${API_BASE_URL}/route`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    country: selectedCountry,
                    tags: selectedTags.map(tag => tagMapping[tag]),
                    maxLandmarks: 10  // 최대 10개의 랜드마크를 요청
                })
            });
            
            if (!response.ok) {
                const errorText = await response.text();
                throw new Error(errorText || '서버 오류가 발생했습니다.');
            }

            const routeData = await response.json();
            
            if (!routeData.route || routeData.route.length === 0) {
                errorDiv.textContent = '선택한 조건에 맞는 경로를 찾을 수 없습니다.';
                errorDiv.style.display = 'block';
                return;
            }

            // 기존 마커와 경로 제거
            clearMarkers();
            if (routeLine) {
                routeLine.setMap(null);
            }

            // 시작 공항 마커 추가
            const airport = routeData.startAirport;
            const airportMarker = new google.maps.Marker({
                position: { lat: airport.latitude, lng: airport.longitude },
                map: map,
                title: airport.name,
                icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
            });
            markers.push(airportMarker);

            // 경로의 랜드마크들 마커 추가
            const path = routeData.route.map(landmark => {
                const marker = new google.maps.Marker({
                    position: { lat: landmark.latitude, lng: landmark.longitude },
                    map: map,
                    title: landmark.name
                });
                markers.push(marker);
                return { lat: landmark.latitude, lng: landmark.longitude };
            });

            // 경로 선 그리기
            routeLine = new google.maps.Polyline({
                path: path,
                geodesic: true,
                strokeColor: '#FF0000',
                strokeOpacity: 1.0,
                strokeWeight: 2
            });
            routeLine.setMap(map);

            // 지도 중심과 줌 레벨 조정
            const bounds = new google.maps.LatLngBounds();
            markers.forEach(marker => bounds.extend(marker.getPosition()));
            map.fitBounds(bounds);

            // 경로 정보 표시
            const routeInfo = document.createElement('div');
            routeInfo.innerHTML = `
                <h3>여행 경로</h3>
                <p>시작 공항: ${airport.name}</p>
                <p>총 거리: ${Math.round(routeData.totalDistance)} km</p>
                <h4>방문 순서:</h4>
                <ol>
                    ${routeData.route.map(landmark => `<li>${landmark.name}</li>`).join('')}
                </ol>
            `;
            document.getElementById('landmarkList').innerHTML = '';
            document.getElementById('landmarkList').appendChild(routeInfo);

        } catch (error) {
            errorDiv.textContent = error.message;
            errorDiv.style.display = 'block';
        } finally {
            hideLoading();
        }
    });

    initializeMap();
});

// 태그 선택기 초기화
function initializeTagSelector() {
    const tagContainer = document.getElementById('tagContainer');
    
    availableTags.forEach(tag => {
        const tagElement = document.createElement('div');
        tagElement.className = 'tag';
        tagElement.textContent = tag;
        tagElement.addEventListener('click', () => {
            tagElement.classList.toggle('selected');
            const selectedTags = document.querySelectorAll('.tag.selected');
            if (selectedTags.length > 5) {
                tagElement.classList.remove('selected');
                showError('태그는 최대 5개까지 선택할 수 있습니다.');
            }
        });
        tagContainer.appendChild(tagElement);
    });
}

// 랜드마크 표시
function displayLandmarks(landmarks) {
    clearMarkers();
    
    landmarks.forEach(landmark => {
        const marker = new google.maps.Marker({
            position: { lat: landmark.latitude, lng: landmark.longitude },
            map: map,
            title: landmark.name
        });
        
        const infoWindow = new google.maps.InfoWindow({
            content: `
                <div class="info-window">
                    <h3>${landmark.name}</h3>
                    <p>${landmark.description || ''}</p>
                    <p>태그: ${landmark.tags.join(', ')}</p>
                </div>
            `
        });
        
        marker.addListener('click', () => {
            if (currentInfoWindow) {
                currentInfoWindow.close();
            }
            infoWindow.open(map, marker);
            currentInfoWindow = infoWindow;
        });
        
        markers.push(marker);
    });
    
    // 모든 마커가 보이도록 지도 범위 조정
    const bounds = new google.maps.LatLngBounds();
    markers.forEach(marker => bounds.extend(marker.getPosition()));
    map.fitBounds(bounds);
}

// 마커 초기화
function clearMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
    if (currentInfoWindow) {
        currentInfoWindow.close();
        currentInfoWindow = null;
    }
}

// 에러 메시지 표시
function showError(message) {
    const errorDiv = document.getElementById('error');
    errorDiv.textContent = message;
    errorDiv.style.display = 'block';
}

// 에러 메시지 제거
function clearError() {
    const errorDiv = document.getElementById('error');
    errorDiv.style.display = 'none';
}

// 로딩 상태 표시
function showLoading() {
    const searchButton = document.getElementById('searchButton');
    searchButton.disabled = true;
    searchButton.textContent = '검색 중...';
}

// 로딩 상태 제거
function hideLoading() {
    const searchButton = document.getElementById('searchButton');
    searchButton.disabled = false;
    searchButton.textContent = '랜드마크 검색';
}

// 구글 맵 초기화
function initializeMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: { lat: 35.0, lng: 127.0 },
        zoom: 5
    });
}

// 최적 경로 표시 함수
function displayRoute(routeData) {
    const routeList = document.getElementById('route-list');
    routeList.innerHTML = '';

    if (!routeData.route || routeData.route.length === 0) {
        routeList.innerHTML = '<div class="route-item">선택한 조건에 맞는 경로를 찾을 수 없습니다.</div>';
        return;
    }

    // 시작 공항 추가
    const airportItem = createRouteItem(0, routeData.startAirport, 'airport');
    routeList.appendChild(airportItem);

    // 랜드마크들 추가
    routeData.route.forEach((location, index) => {
        if (location.type === 'LANDMARK') {
            const landmarkItem = createRouteItem(index + 1, location, 'landmark');
            routeList.appendChild(landmarkItem);
        }
    });

    // 총 소요 시간 표시
    const totalTimeItem = document.createElement('div');
    totalTimeItem.className = 'route-item total-time';
    totalTimeItem.innerHTML = `
        <div class="route-content">
            <div class="route-info">
                총 예상 소요 시간: ${Math.round(routeData.totalTime * 10) / 10}시간
            </div>
        </div>
    `;
    routeList.appendChild(totalTimeItem);

    // 지도에 경로 표시
    updateMapRoute(routeData);
}

function createRouteItem(index, location, type) {
    const item = document.createElement('div');
    item.className = 'route-item';
    
    let content = `
        <div class="route-number">${index}</div>
        <div class="route-content">
            <div class="route-type ${type}">${type === 'airport' ? '공항' : '관광지'}</div>
            <div class="route-name">${location.name}</div>
            <div class="route-info">
    `;

    if (type === 'airport') {
        content += `${location.code} - ${location.country}`;
    } else {
        content += `예상 방문 시간: ${location.estimatedTime}시간`;
    }

    content += `
            </div>
        </div>
    `;

    item.innerHTML = content;
    return item;
}

function updateMapRoute(routeData) {
    const { startAirport, route, totalDistance } = routeData;
    
    // 디버깅을 위해 검색된 랜드마크 리스트를 화면에 표시
    const landmarksListElement = document.getElementById('landmarks-list');
    landmarksListElement.innerHTML = '<h3>Selected Landmarks:</h3><ul>' +
        route.map(landmark => `<li>${landmark.name}</li>`).join('') + '</ul>';

    clearMarkers();
    
    // 이전 경로선이 있다면 제거
    if (routeLine) {
        routeLine.setMap(null);
    }

    const path = [];
    const bounds = new google.maps.LatLngBounds();

    // 시작 공항 마커 추가
    const airport = routeData.startAirport;
    const airportMarker = new google.maps.Marker({
        position: { lat: airport.latitude, lng: airport.longitude },
        map: map,
        title: airport.name,
        icon: {
            url: 'images/airport.png',
            scaledSize: new google.maps.Size(32, 32)
        }
    });
    markers.push(airportMarker);
    path.push({ lat: airport.latitude, lng: airport.longitude });
    bounds.extend(airportMarker.getPosition());

    // 경로상의 랜드마크 마커 추가
    routeData.route.forEach((landmark, index) => {
        if (landmark !== routeData.startAirport) {
            const marker = new google.maps.Marker({
                position: { lat: landmark.latitude, lng: landmark.longitude },
                map: map,
                title: landmark.name,
                label: (index).toString()
            });
            markers.push(marker);
            path.push({ lat: landmark.latitude, lng: landmark.longitude });
            bounds.extend(marker.getPosition());

            // 정보창 생성
            const infoWindow = new google.maps.InfoWindow({
                content: `
                    <div class="info-window">
                        <h3>${landmark.name}</h3>
                        <p>${landmark.description || ''}</p>
                        <p>태그: ${landmark.tags.join(', ')}</p>
                    </div>
                `
            });

            marker.addListener('click', () => {
                if (currentInfoWindow) {
                    currentInfoWindow.close();
                }
                infoWindow.open(map, marker);
                currentInfoWindow = infoWindow;
            });
        }
    });

    // 경로선 그리기
    routeLine = new google.maps.Polyline({
        path: path,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 1.0,
        strokeWeight: 2
    });
    routeLine.setMap(map);

    // 모든 마커가 보이도록 지도 조정
    map.fitBounds(bounds);
}

function addMarker(landmark) {
    const marker = new google.maps.Marker({
        position: { lat: landmark.latitude, lng: landmark.longitude },
        map: map,
        title: landmark.name
    });
    
    const infoWindow = new google.maps.InfoWindow({
        content: `
            <div class="info-window">
                <h3>${landmark.name}</h3>
                <p>${landmark.description || ''}</p>
                <p>태그: ${landmark.tags.join(', ')}</p>
            </div>
        `
    });
    
    marker.addListener('click', () => {
        if (currentInfoWindow) {
            currentInfoWindow.close();
        }
        infoWindow.open(map, marker);
        currentInfoWindow = infoWindow;
    });
    
    markers.push(marker);
}
