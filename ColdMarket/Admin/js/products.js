document.addEventListener('DOMContentLoaded', function() {
    // Update this to match your actual API base URL
    const API_BASE_URL = 'http://localhost:9090/coldMarket';
    const authToken = localStorage.getItem('authToken');
    
    if (!authToken) {
        window.location.href = '/login.html';
        return;
    }
    // DOM Elements
    const productsTable = document.getElementById('productsTable')?.getElementsByTagName('tbody')[0];
    const productModal = document.getElementById('productModal');
    const productForm = document.getElementById('productForm');
    const imagePreview = document.getElementById('imagePreview');
    const imageInput = document.getElementById('productImages');
    const addProductBtn = document.getElementById('addProductBtn');
    const modalCloseBtn = document.querySelector('.close'); // Renamed to avoid conflict

    // Check if required elements exist
    if (!productsTable || !productModal || !productForm || !imagePreview || !imageInput || !addProductBtn || !modalCloseBtn) {
        console.error('Required DOM elements not found');
        return;
    }

    let currentProductId = null;
    let uploadedImages = [];

    // Initialize the page
    loadProducts();

    // Event Listeners
    addProductBtn.addEventListener('click', openProductModal); // Renamed function
    modalCloseBtn.addEventListener('click', closeProductModal); // Renamed function
    productForm.addEventListener('submit', handleFormSubmit);
    imageInput.addEventListener('change', handleImageUpload);

    // Functions
    async function loadProducts() {
        try {
            showLoading(true);
            const response = await fetch(`${API_BASE_URL}/products/all`, {
                headers: {
                    'Authorization': `Bearer ${authToken}`
                }
            });
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to fetch products');
            }
            
            const data = await response.json();
            renderProducts(data.data);
        } catch (error) {
            showError(error.message);
            console.error('Load products error:', error);
        } finally {
            showLoading(false);
        }
    }

    function renderProducts(products) {
        productsTable.innerHTML = '';
        
        if (!products || products.length === 0) {
            const row = document.createElement('tr');
            row.innerHTML = `<td colspan="7" class="text-center">No products found</td>`;
            productsTable.appendChild(row);
            return;
        }

        products.forEach(product => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${product.id || ''}</td>
                <td><img src="${getFirstImageUrl(product)}" alt="${product.name}" width="50"></td>
                <td>${product.name}</td>
                <td>$${product.price?.toFixed(2) || '0.00'}</td>
                <td>${product.stock || 0}</td>
                <td>${product.category || ''}</td>
                <td>
                    <button class="btn btn-primary btn-sm edit-btn" data-id="${product.id}">Edit</button>
                    <button class="btn btn-danger btn-sm delete-btn" data-id="${product.id}">Delete</button>
                </td>
            `;
            productsTable.appendChild(row);
        });

        // Add event listeners to buttons
        document.querySelectorAll('.edit-btn').forEach(btn => {
            btn.addEventListener('click', () => editProduct(btn.dataset.id));
        });
        
        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', () => deleteProduct(btn.dataset.id));
        });
    }

    function getFirstImageUrl(product) {
        // Implement actual image URL logic based on your API
        return product.imageUrl || 'https://via.placeholder.com/50';
    }

    function openProductModal(product = null) {
        currentProductId = product ? product.id : null;
        document.getElementById('modalTitle').textContent = product ? 'Edit Product' : 'Add New Product';
        
        if (product) {
            document.getElementById('productName').value = product.name || '';
            document.getElementById('productDescription').value = product.description || '';
            document.getElementById('productPrice').value = product.price || '';
            document.getElementById('productStock').value = product.stock || '';
            document.getElementById('productCategory').value = product.category || '';
        } else {
            productForm.reset();
            imagePreview.innerHTML = '';
            uploadedImages = [];
        }
        
        productModal.style.display = 'flex';
    }

    function closeProductModal() {
        productModal.style.display = 'none';
    }

    function handleImageUpload(e) {
        const files = Array.from(e.target.files);
        uploadedImages = [...uploadedImages, ...files];
        
        imagePreview.innerHTML = '';
        uploadedImages.forEach(file => {
            const reader = new FileReader();
            reader.onload = (event) => {
                const img = document.createElement('img');
                img.src = event.target.result;
                img.style.width = '100px';
                img.style.height = '100px';
                img.style.objectFit = 'cover';
                img.style.margin = '5px';
                imagePreview.appendChild(img);
            };
            reader.readAsDataURL(file);
        });
    }

    async function handleFormSubmit(e) {
        e.preventDefault();
        
        try {
            showLoading(true);
            
            const productData = {
                name: document.getElementById('productName').value,
                description: document.getElementById('productDescription').value,
                price: parseFloat(document.getElementById('productPrice').value),
                stock: parseInt(document.getElementById('productStock').value),
                category: document.getElementById('productCategory').value
            };

            let response;
            
            if (currentProductId) {
                // Update existing product
                response = await fetch(`${API_BASE_URL}/products/product/update/${currentProductId}`, {
                    method: 'PUT',
                    headers: {
                        'Authorization': `Bearer ${authToken}`,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(productData)
                });
            } else {
                // Add new product
                response = await fetch(`${API_BASE_URL}/products/add`, {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${authToken}`,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(productData)
                });
            }
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Operation failed');
            }

            const result = await response.json();
            
            // Upload images if we have any and this is a new product
            if (uploadedImages.length > 0 && !currentProductId) {
                await uploadImages(result.data.id);
            }
            
            showSuccess(currentProductId ? 'Product updated successfully!' : 'Product added successfully!');
            closeProductModal();
            loadProducts();
        } catch (error) {
            showError(error.message);
            console.error('Form submission error:', error);
        } finally {
            showLoading(false);
        }
    }

    async function editProduct(productId) {
        try {
            showLoading(true);
            const response = await fetch(`${API_BASE_URL}/products/product/id/${productId}`, {
                headers: {
                    'Authorization': `Bearer ${authToken}`
                }
            });
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to fetch product');
            }
            
            const data = await response.json();
            openProductModal(data.data);
        } catch (error) {
            showError(error.message);
            console.error('Edit product error:', error);
        } finally {
            showLoading(false);
        }
    }

    async function deleteProduct(productId) {
        if (!confirm('Are you sure you want to delete this product?')) return;
        
        try {
            showLoading(true);
            const response = await fetch(`${API_BASE_URL}/products/product/delete/${productId}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${authToken}`
                }
            });
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to delete product');
            }
            
            showSuccess('Product deleted successfully!');
            loadProducts();
        } catch (error) {
            showError(error.message);
            console.error('Delete product error:', error);
        } finally {
            showLoading(false);
        }
    }

    async function uploadImages(productId) {
        if (uploadedImages.length === 0) return;
        
        try {
            const formData = new FormData();
            uploadedImages.forEach(file => {
                formData.append('file', file);
            });
            formData.append('ProductId', productId);
            
            const response = await fetch(`${API_BASE_URL}/images/upload`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${authToken}`
                },
                body: formData
            });
            
            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Failed to upload images');
            }
            
            return await response.json();
        } catch (error) {
            console.error('Image upload error:', error);
            throw error;
        }
    }

    // Utility Functions
    function showLoading(show) {
        const loadingElement = document.getElementById('loading');
        if (loadingElement) {
            loadingElement.style.display = show ? 'block' : 'none';
        }
    }

    function showSuccess(message) {
        const messageDiv = document.getElementById('message');
        if (messageDiv) {
            messageDiv.textContent = message;
            messageDiv.className = 'message success';
            messageDiv.style.display = 'block';
            setTimeout(() => {
                messageDiv.style.display = 'none';
            }, 3000);
        }
    }

    function showError(message) {
        const messageDiv = document.getElementById('message');
        if (messageDiv) {
            messageDiv.textContent = message;
            messageDiv.className = 'message error';
            messageDiv.style.display = 'block';
            setTimeout(() => {
                messageDiv.style.display = 'none';
            }, 5000);
        }
    }
});