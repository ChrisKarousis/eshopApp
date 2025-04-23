document.addEventListener("DOMContentLoaded", () => {
    const username = sessionStorage.getItem("username");
    fetch(`/eshop/orders/user/${username}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to fetch orders");
            return res.json();
        })
        .then(data => displayOrders(data))
        .catch(err => console.error("Error loading orders:", err));
});

function completeOrder(orderId) {
    fetch(`http://localhost:8080/eshop/orders/${orderId}/complete`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to complete order");
            return res.text();
        })
        .then(message => {
            alert(message);
            location.reload();
        })
        .catch(err => console.error("Error completing order:", err));
}

function deleteOrder(orderId) {
    if (!confirm("Are you sure you want to delete this order?")) return;

    fetch(`/eshop/orders/${orderId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(res => {
            if (!res.ok) throw new Error("Failed to delete order");
            alert("Order deleted!");
            location.reload();
        })
        .catch(err => console.error("Error deleting order:", err));
}


function displayOrders(orders) {

    const container = document.getElementById("ordersList");

    if (orders.length === 0) {
        container.innerHTML = "<p>No orders found.</p>";
        return;
    }

    if (!Array.isArray(orders)) {
        orders = [orders]; // wrap single object into an array
    }

    orders.forEach(order => {
        const orderDiv = document.createElement("div");
        orderDiv.className = "order";

        let itemsHtml = order.items.map(item => `
            <li>${item.product.name} (x${item.quantity}) - €${item.subtotal}
            <img src="${item.product.image}" alt="${item.product.name}" style="width: 50px; height: 50px;"/></li>
        `).join("");

        orderDiv.innerHTML = `
            <h3>Order #${order.id} - ${order.status}</h3>
            <p>Total: €${order.totalPrice}</p>
            <ul>${itemsHtml}</ul>
            <button class="complete-btn" onclick="completeOrder(${order.id})">Complete Order</button>
            <button class="delete-btn" onclick="deleteOrder(${order.id})">Delete Order</button>
        `;

        container.appendChild(orderDiv);
    });
}
