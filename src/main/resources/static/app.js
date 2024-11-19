// Global Variables
let map;
let selectedCountry = null;
let selectedTags = [];
let landmarks = [];
let markers = [];
let routePath = null;

// Initialize the map
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 5,
        center: { lat: 35.0, lng: 135.0 } // Centered on East Asia
    });
}

// Fetch tags and populate tag selection
function loadTags() {
    const tags = ['history', 'nature', 'culture', 'food', 'adventure', 'architecture'];
    const tagSelectionDiv = document.getElementById('tagSelection');

    tags.forEach(tag => {
        const checkbox = document.createElement('input');
        checkbox.type = 'checkbox';
        checkbox.id = tag;
        checkbox.value = tag;

        const label = document.createElement('label');
        label.htmlFor = tag;
        label.innerText = tag;

        tagSelectionDiv.appendChild(checkbox);
        tagSelectionDiv.appendChild(label);
        tagSelectionDiv.appendChild(document.createElement('br'));
    });
}

// Validate tag selection
function validateTagSelection() {
    const checkboxes = document.querySelectorAll('#tagSelection input[type="checkbox"]:checked');
    selectedTags = Array.from(checkboxes).map(cb => cb.value);

    if (selectedTags.length < 2 || selectedTags.length > 5) {
        alert('Please select between 2 and 5 tags.');
        return false;
    }
    return true;
}

// Fetch landmarks from backend
function fetchLandmarks() {
    if (!validateTagSelection()) return;

    selectedCountry = document.getElementById('countrySelect').value;

    fetch('/getLandmarks', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            country: selectedCountry,
            tags: selectedTags
        })
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.json();
        })
        .then(data => {
            landmarks = data;
            displayLandmarksOnMap();
            fetchOptimizedRoute();
        })
        .catch(error => {
            alert('An error occurred: ' + error.message);
        });
}

// Display landmarks on the map
function displayLandmarksOnMap() {
    clearMarkers();

    landmarks.forEach(landmark => {
        const position = { lat: landmark.latitude, lng: landmark.longitude };
        const marker = new google.maps.Marker({
            position: position,
            map: map,
            title: landmark.name
        });

        // Add click listener for info window
        const infoWindow = new google.maps.InfoWindow({
            content: `<h3>${landmark.name}</h3><p>Tags: ${landmark.tags.join(', ')}</p>`
        });
        marker.addListener('click', function () {
            infoWindow.open(map, marker);
        });

        markers.push(marker);
    });

    // Adjust map bounds to fit all markers
    const bounds = new google.maps.LatLngBounds();
    markers.forEach(marker => bounds.extend(marker.getPosition()));
    map.fitBounds(bounds);
}

// Fetch optimized route from backend
function fetchOptimizedRoute() {
    fetch('/getOptimizedRoute', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(landmarks)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.json();
        })
        .then(data => {
            landmarks = data;
            drawRouteOnMap();
            displayLandmarkInfo();
        })
        .catch(error => {
            alert('An error occurred: ' + error.message);
        });
}

// Draw the route on the map
function drawRouteOnMap() {
    if (routePath) {
        routePath.setMap(null);
    }

    const routeCoordinates = landmarks.map(landmark => ({
        lat: landmark.latitude,
        lng: landmark.longitude
    }));

    routePath = new google.maps.Polyline({
        path: routeCoordinates,
        geodesic: true,
        strokeColor: '#FF0000',
        strokeOpacity: 0.8,
        strokeWeight: 2
    });

    routePath.setMap(map);
}

// Display landmark information
function displayLandmarkInfo() {
    const infoDiv = document.getElementById('landmarkInfo');
    infoDiv.innerHTML = '<h2>Landmark Details</h2>';

    landmarks.forEach(landmark => {
        const info = document.createElement('div');
        info.innerHTML = `<h4>${landmark.visitOrder}. ${landmark.name}</h4><p>Tags: ${landmark.tags.join(', ')}</p>`;
        infoDiv.appendChild(info);
    });
}

// Clear existing markers from the map
function clearMarkers() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
}

// Event listeners
document.getElementById('submitBtn').addEventListener('click', fetchLandmarks);

// Initialize
initMap();
loadTags();
