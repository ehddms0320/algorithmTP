let map;
let markers = [];
const API_BASE_URL = 'http://localhost:8081/api';

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    initializeMap();
    loadCountries();
    loadTags();
    setupEventListeners();
});

// Google Maps 초기화
function initializeMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 4,
        center: { lat: 35.0, lng: 127.0 }, // 동아시아 중심
    });
}

// 국가 목록 로드
async function loadCountries() {
    try {
        const response = await fetch(`${API_BASE_URL}/countries`);
        const data = await response.json();
        
        const countrySelect = document.getElementById('countrySelect');
        data.countries.forEach(country => {
            const option = document.createElement('option');
            option.value = country;
            option.textContent = country;
            countrySelect.appendChild(option);
        });
    } catch (error) {
        showError('Error loading countries. Please refresh the page.');
    }
}

// 태그 목록 로드
async function loadTags() {
    try {
        const response = await fetch(`${API_BASE_URL}/tags`);
        const data = await response.json();
        
        const tagContainer = document.getElementById('tagContainer');
        data.tags.forEach(tag => {
            const tagElement = document.createElement('div');
            tagElement.className = 'tag';
            tagElement.textContent = tag;
            tagElement.addEventListener('click', () => {
                tagElement.classList.toggle('selected');
                const selectedTags = document.querySelectorAll('.tag.selected');
                if (selectedTags.length > 5) {
                    tagElement.classList.remove('selected');
                    showError('You can select up to 5 tags');
                }
            });
            tagContainer.appendChild(tagElement);
        });
    } catch (error) {
        showError('Error loading tags. Please refresh the page.');
    }
}

// 에러 메시지 표시
function showError(message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    document.querySelector('.container').insertBefore(errorDiv, document.querySelector('.results-section'));
    
    setTimeout(() => {
        errorDiv.remove();
    }, 3000);
}

// 이벤트 리스너 설정
function setupEventListeners() {
    const countrySelect = document.getElementById('countrySelect');
    const searchButton = document.getElementById('searchButton');

    countrySelect.addEventListener('change', async () => {
        const country = countrySelect.value;
        if (country) {
            await loadLandmarks(country);
        }
    });

    searchButton.addEventListener('click', handleSearch);
}

// 랜드마크 로드
async function loadLandmarks(country) {
    try {
        const response = await fetch(`${API_BASE_URL}/landmarks?country=${country}`);
        const landmarks = await response.json();
        displayLandmarks(landmarks);
        updateMap(landmarks);
    } catch (error) {
        console.error('Error loading landmarks:', error);
    }
}

// 랜드마크 표시
function displayLandmarks(landmarks) {
    const container = document.getElementById('landmarks');
    container.innerHTML = '';

    landmarks.forEach(landmark => {
        const card = document.createElement('div');
        card.className = 'landmark-card';
        card.innerHTML = `
            <h3>${landmark.name}</h3>
            <div class="landmark-tags">
                ${landmark.tags.map(tag => `<span class="landmark-tag">${tag}</span>`).join('')}
            </div>
        `;
        container.appendChild(card);
    });
}

// 지도 업데이트
function updateMap(landmarks) {
    // 기존 마커 제거
    markers.forEach(marker => marker.setMap(null));
    markers = [];

    // 새 마커 추가
    landmarks.forEach((landmark, index) => {
        const marker = new google.maps.Marker({
            position: { lat: landmark.latitude, lng: landmark.longitude },
            map: map,
            title: landmark.name,
            label: (index + 1).toString()
        });

        // 정보창 추가
        const infoWindow = new google.maps.InfoWindow({
            content: `
                <div>
                    <h3>${landmark.name}</h3>
                    <p>Tags: ${landmark.tags.join(', ')}</p>
                    <p>Order: ${index + 1}</p>
                </div>
            `
        });

        marker.addListener('click', () => {
            infoWindow.open(map, marker);
        });

        markers.push(marker);
    });

    // 지도 범위 조정
    if (markers.length > 0) {
        const bounds = new google.maps.LatLngBounds();
        markers.forEach(marker => bounds.extend(marker.getPosition()));
        map.fitBounds(bounds);
    }
}

// 검색 처리
async function handleSearch() {
    const country = document.getElementById('countrySelect').value;
    const selectedTags = Array.from(document.querySelectorAll('.tag.selected'))
        .map(tag => tag.textContent);

    if (!country || selectedTags.length < 2 || selectedTags.length > 5) {
        alert('Please select a country and 2-5 tags');
        return;
    }

    try {
        const response = await fetch(`${API_BASE_URL}/search`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                country: country,
                tags: selectedTags
            })
        });

        const landmarks = await response.json();
        displayLandmarks(landmarks);
        updateMap(landmarks);
    } catch (error) {
        console.error('Error searching landmarks:', error);
    }
}
