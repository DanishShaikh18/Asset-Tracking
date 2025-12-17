// File: src/main/resources/static/js/app.js

const API_URL = '/api';

let allAssets = [];
let allUsers = [];

// =====================
// Load all assets
// =====================
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

// =====================
// Display assets
// =====================
function displayAssets(assets) {
    const tbody = document.getElementById('assetTableBody');

    if (assets.length === 0) {
        tbody.innerHTML =
            '<tr><td colspan="5" class="text-center">No assets found</td></tr>';
        return;
    }

    tbody.innerHTML = assets.map(asset => {
        const statusClass = asset.status === 'AVAILABLE'
            ? 'badge-success'
            : 'badge-danger';

        const isAvailable = asset.status === 'AVAILABLE';

        return `
            <tr>
                <td>${asset.id}</td>
                <td>${asset.name}</td>
                <td>${asset.type}</td>
                <td>
                    <span class="badge ${statusClass}">
                        ${asset.status}
                    </span>
                </td>
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

// =====================
// Load all users
// =====================
async function loadUsers() {
    try {
        const response = await fetch(`${API_URL}/users`);
        allUsers = await response.json();
        populateUserSelect();
    } catch (error) {
        console.error('Error loading users:', error);
    }
}

// =====================
// Populate user dropdown
// =====================
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

// =====================
// Add Asset
// =====================
document.getElementById('addAssetForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = document.getElementById('assetName').value;
    const type = document.getElementById('assetType').value;

    try {
        const response = await fetch(`${API_URL}/assets`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, type })
        });

        if (response.ok) {
            alert('Asset added successfully!');
            e.target.reset();
            loadAssets();
        } else {
            alert('Error adding asset');
        }
    } catch (error) {
        console.error('Error adding asset:', error);
        alert('Error adding asset');
    }
});

// =====================
// Search Assets
// =====================
document.getElementById('searchInput').addEventListener('input', (e) => {
    const term = e.target.value.toLowerCase();

    const filtered = allAssets.filter(asset =>
        asset.name.toLowerCase().includes(term) ||
        asset.type.toLowerCase().includes(term)
    );

    displayAssets(filtered);
});

// =====================
// Assign Asset
// =====================
function openAssignModal(assetId, assetName) {
    document.getElementById('assignAssetId').value = assetId;
    document.getElementById('assignAssetName').textContent = assetName;
    document.getElementById('assignModal').style.display = 'block';
}

document.querySelector('.close').addEventListener('click', () => {
    document.getElementById('assignModal').style.display = 'none';
});

document.getElementById('assignForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const assetId = Number(document.getElementById('assignAssetId').value);
    const userId = Number(document.getElementById('assignUserId').value);

    try {
        const response = await fetch(`${API_URL}/assign`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ assetId, userId })
        });

        if (response.ok) {
            alert('Asset assigned successfully!');
            document.getElementById('assignModal').style.display = 'none';
            loadAssets();
        } else {
            const err = await response.json();
            alert(err.error || 'Error assigning asset');
        }
    } catch (error) {
        console.error('Error assigning asset:', error);
    }
});

// =====================
// Return Asset
// =====================
function openReturnModal(assetId, assetName) {
    document.getElementById('returnAssetId').value = assetId;
    document.getElementById('returnAssetName').textContent = assetName;
    document.getElementById('returnModal').style.display = 'block';
}

document.querySelector('.close-return').addEventListener('click', () => {
    document.getElementById('returnModal').style.display = 'none';
});

document.getElementById('returnForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const assetId = Number(document.getElementById('returnAssetId').value);

    try {
        const response = await fetch(`${API_URL}/return`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ assetId })
        });

        if (response.ok) {
            alert('Asset returned successfully!');
            document.getElementById('returnModal').style.display = 'none';
            loadAssets();
        } else {
            const err = await response.json();
            alert(err.error || 'Error returning asset');
        }
    } catch (error) {
        console.error('Error returning asset:', error);
    }
});

// =====================
// Close modals on outside click
// =====================
window.addEventListener('click', (e) => {
    if (e.target.id === 'assignModal') {
        e.target.style.display = 'none';
    }
    if (e.target.id === 'returnModal') {
        e.target.style.display = 'none';
    }
});

// =====================
// Initial Load
// =====================
loadAssets();
loadUsers();
