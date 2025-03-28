import axios from 'axios';

const api = axios.create({
    baseURL: '/api'
});

export const productApi = {
    getAllProducts() {
        return api.get('/products');
    },
    
    getProductById(id) {
        return api.get(`/products/${id}`);
    },
    
    getProductsByCategory(category) {
        return api.get(`/products/category/${category}`);
    },
    
    searchProducts(keyword) {
        return api.get('/products/search', { params: { keyword } });
    }
};

export const cartApi = {
    getUserCart(userId) {
        return api.get(`/cart/${userId}`);
    },
    
    addToCart(cartItem) {
        return api.post('/cart', cartItem);
    },
    
    updateCartItemQuantity(cartId, quantity) {
        return api.put(`/cart/${cartId}`, null, { params: { quantity } });
    },
    
    removeFromCart(cartId) {
        return api.delete(`/cart/${cartId}`);
    },
    
    clearUserCart(userId) {
        return api.delete(`/cart/user/${userId}`);
    }
};

export const orderApi = {
    getOrderById(id) {
        return api.get(`/orders/${id}`);
    },
    
    getUserOrders(userId) {
        return api.get(`/orders/user/${userId}`);
    },
    
    createOrder(userId, shippingAddress) {
        return api.post(`/orders/user/${userId}`, null, {
            params: { shippingAddress }
        });
    },
    
    updateOrderStatus(orderId, status) {
        return api.put(`/orders/${orderId}/status`, null, {
            params: { status }
        });
    },
    
    cancelOrder(orderId) {
        return api.post(`/orders/${orderId}/cancel`);
    },
    
    getOrderDetails(orderId) {
        return api.get(`/orders/${orderId}/details`);
    }
}; 