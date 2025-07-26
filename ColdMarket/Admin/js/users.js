document.addEventListener('DOMContentLoaded', function() {
    // DOM Elements
    const usersTable = document.getElementById('usersTable').getElementsByTagName('tbody')[0];
    const userModal = document.getElementById('userModal');
    const closeModal = document.querySelector('#userModal .close');
    const userForm = document.getElementById('userForm');
    
    // Sample data - replace with actual API calls
    let users = [
        {
            id: 1,
            name: 'John Doe',
            email: 'john@example.com',
            role: 'user',
            registered: '2023-01-15'
        },
        {
            id: 2,
            name: 'Jane Smith',
            email: 'jane@example.com',
            role: 'admin',
            registered: '2023-02-20'
        }
    ];
    
    // Load users into table
    function loadUsers() {
        usersTable.innerHTML = '';
        users.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>
                    <select class="role-select" data-id="${user.id}">
                        <option value="user" ${user.role === 'user' ? 'selected' : ''}>User</option>
                        <option value="admin" ${user.role === 'admin' ? 'selected' : ''}>Admin</option>
                    </select>
                </td>
                <td>${user.registered}</td>
                <td>
                    <button class="btn btn-primary btn-sm edit-btn" data-id="${user.id}">Edit</button>
                    <button class="btn btn-danger btn-sm delete-btn" data-id="${user.id}">Delete</button>
                </td>
            `;
            usersTable.appendChild(row);
        });
        
        // Add event listeners
        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', editUser);
        });
        
        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', deleteUser);
        });
        
        document.querySelectorAll('.role-select').forEach(select => {
            select.addEventListener('change', updateUserRole);
        });
    }
    
    // Close modal
    closeModal.addEventListener('click', function() {
        userModal.style.display = 'none';
    });
    
    // Handle form submission
    userForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const userId = document.getElementById('userId').value;
        const userData = {
            name: document.getElementById('userName').value,
            email: document.getElementById('userEmail').value,
            role: document.getElementById('userRole').value,
            password: document.getElementById('userPassword').value
        };
        
        if (userId) {
            // Update existing user
            const index = users.findIndex(u => u.id == userId);
            if (index !== -1) {
                users[index] = { ...users[index], ...userData };
            }
        } else {
            // Add new user (not implemented in this example)
        }
        
        loadUsers();
        userModal.style.display = 'none';
    });
    
    // Edit user
    function editUser(e) {
        const userId = e.target.getAttribute('data-id');
        const user = users.find(u => u.id == userId);
        
        if (user) {
            document.getElementById('userModalTitle').textContent = 'Edit User';
            document.getElementById('userId').value = user.id;
            document.getElementById('userName').value = user.name;
            document.getElementById('userEmail').value = user.email;
            document.getElementById('userRole').value = user.role;
            document.getElementById('userPassword').value = '';
            
            userModal.style.display = 'flex';
        }
    }
    
    // Delete user
    function deleteUser(e) {
        if (confirm('Are you sure you want to delete this user?')) {
            const userId = e.target.getAttribute('data-id');
            users = users.filter(u => u.id != userId);
            loadUsers();
        }
    }
    
    // Update user role
    function updateUserRole(e) {
        const userId = e.target.getAttribute('data-id');
        const newRole = e.target.value;
        
        const user = users.find(u => u.id == userId);
        if (user) {
            user.role = newRole;
            // In a real app, you would make an API call here to update the role
        }
    }
    
    // Initial load
    loadUsers();
});