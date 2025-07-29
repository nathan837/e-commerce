// Updated API configuration
const API_BASE_URL = 'http://localhost:9090'; // Remove double slash
const API_PREFIX = '/coldMarket';

// Updated fetchData function
async function fetchData(endpoint) {
    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                // Only include if you have authentication
                // 'Authorization': `Bearer ${localStorage.getItem('adminToken')}`
            },
            mode: 'cors' // Explicitly enable CORS
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        return await response.json();
    } catch (error) {
        console.error(`Error fetching ${endpoint}:`, error);
        return null;
    }
}

// Temporary test function
async function testEndpoints() {
    const endpoints = [
        `${API_PREFIX}/products/count`,
        `${API_PREFIX}/user/2/user`,
        `${API_PREFIX}/user/count`
    ];
    
    for (const endpoint of endpoints) {
        try {
            const data = await fetchData(endpoint);
            console.log(`${endpoint} response:`, data);
        } catch (error) {
            console.error(`${endpoint} failed:`, error);
        }
    }
}

// Call this first to verify connections
document.addEventListener('DOMContentLoaded', testEndpoints);