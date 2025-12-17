// File: src/main/resources/static/js/app.js

const API_URL = 'http://localhost:8080/api';

let allAssets = [];
let allUsers = [];

// Load all assets
async function loadAssets() {
    try {
        const response = await fetch(`${API_URL}/assets`);
        allAssets = await response.json();
        displayAssets(allAssets);
    } catch (error) {
        console.error('Error loading assets:', error);
        document.getElementById('assetTableBody').innerHTML = 
            '<tr><td colspan="5" class="text-center error">Error loading assets</td></tr>';
    }
}

// Display assets in table
function displayAssets(assets) {
    const tbody = document.getElementById('assetTableBody');
    
    if (assets.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">No assets found</td></tr>';
        return;
    }
    
    tbody.innerHTML = assets.map(asset => {
        const statusClass = asset.status === 'AVAILABLE' ? 'badge-success' : 'badge-danger';
        const isAvailable = asset.status === 'AVAILABLE';
        
        return `
            <tr>
                <td>${asset.id}</td>
                <td>${asset.name}</td>
                <td>${asset.type}</td>
                <td><span class="badge ${statusClass}">${asset.status}</span></td>
                <td>
                    <button class="btn btn-primary btn-sm" 
                            onclick="openAssignModal(${asset.id}, '${asset.name}')"
                            ${!isAvailable ? 'disabled' : ''}>
                        Assign
                    </button>
                    <button class="btn btn-success btn-sm" 
                            onclick="openReturnModal(${asset.id}, '${asset.name}')"
                            ${isAvailable ? 'disabled' : ''}>
                        Return
                    </button>
                </td>
            </tr>
        `;
    }).join('');
}

// Load all users
async function loadUsers() {
    try {
        const response = await fetch(`${API_URL}/users`);
        allUsers = await response.json();
        populateUserSelect();
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

// Populate user select dropdown
function populateUserSelect() {
    const select = document.getElementById('assignUserId');
    select.innerHTML = '<option value="">-- Select User --</option>';
    
    allUsers.forEach(user => {
        const option = document.createElement('option');
        option.value = user.id;
        option.textContent = `${user.name} (${user.email})`;
        select.appendChild(option);
    });
}

// Add asset form submission
document.getElementById('addAssetForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const name = document.getElementById('assetName').value;
    const type = document.getElementById('assetType').value;
    
    try {
        const response = await fetch(`${API_URL}/assets`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ name, type })
        });
        
        if (response.ok) {
            alert('Asset added successfully!');
            document.getElementById('addAssetForm').reset();
            loadAssets();
        } else {
            alert('Error adding asset');
        }
    } catch (error) {
        console.error('Error adding asset:', error);
        alert('Error adding asset');
    }
});

// Search functionality
document.getElementById('searchInput').addEventListener('input', (e) => {
    const searchTerm = e.target.value.toLowerCase();
    
    const filteredAssets = allAssets.filter(asset => 
        asset.name.toLowerCase().includes(searchTerm) || 
        asset.type.toLowerCase().includes(searchTerm)
    );
    
    displayAssets(filteredAssets);
});

// Open assign modal
function openAssignModal(assetId, assetName) {
    document.getElementById('assignAssetId').value = assetId;
    document.getElementById('assignAssetName').textContent = assetName;
    document.getElementById('assignModal').style.display = 'block';
}

// Close assign modal
document.querySelector('.close').addEventListener('click', () => {
    document.getElementById('assignModal').style.display = 'none';
});

// Assign asset form submission
document.getElementById('assignForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const assetId = parseInt(document.getElementById('assignAssetId').value);
    const userId = parseInt(document.getElementById('assignUserId').value);
    
    try {
        const response = await fetch(`${API_URL}/assign`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ assetId, userId })
        });
        
        if (response.ok) {
            alert('Asset assigned successfully!');
            document.getElementById('assignModal').style.display = 'none';
            document.getElementById('assignForm').reset();
            loadAssets();
        } else {
            const error = await response.json();
            alert(error.error || 'Error assigning asset');
        }
    } catch (error) {
        console.error('Error assigning asset:', error);
        alert('Error assigning asset');
    }
});

// Open return modal
function openReturnModal(assetId, assetName) {
    document.getElementById('returnAssetId').value = assetId;
    document.getElementById('returnAssetName').textContent = assetName;
    document.getElementById('returnModal').style.display = 'block';
}

// Close return modal
document.querySelector('.close-return').addEventListener('click', () => {
    document.getElementById('returnModal').style.display = 'none';
});

// Return asset form submission
document.getElementById('returnForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const assetId = parseInt(document.getElementById('returnAssetId').value);
    
    try {
        const response = await fetch(`${API_URL}/return`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ assetId })
        });
        
        if (response.ok) {
            alert('Asset returned successfully!');
            document.getElementById('returnModal').style.display = 'none';
            loadAssets();
        } else {
            const error = await response.json();
            alert(error.error || 'Error returning asset');
        }
    } catch (error) {
        console.error('Error returning asset:', error);
        alert('Error returning asset');
    }
});

// Close modals when clicking outside
window.addEventListener('click', (e) => {
    const assignModal = document.getElementById('assignModal');
    const returnModal = document.getElementById('returnModal');
    
    if (e.target === assignModal) {
        assignModal.style.display = 'none';
    }
    if (e.target === returnModal) {
        returnModal.style.display = 'none';
    }
});

// Load data on page load
loadAssets();
loadUsers();