document.addEventListener("DOMContentLoaded", () => {
    fetch("/eshop/products") // Adjust this if your endpoint is different
        .then(response => response.json())
        .then(data => renderItems(data))
        .catch(error => console.error("Error fetching items:", error));
});

function renderItems(items) {
    const itemsBody = document.getElementById("itemsBody");
    itemsBody.innerHTML = "";

    items.forEach(item => {
        const row = document.createElement("tr");

        if(item.stock > 0)
        {
            row.innerHTML = `
                <td>${item.name}</td>
                <td>${item.description || "-"}</td>
                <td>${item.price.toFixed(2)}</td>
                <td>${item.stock}</td>
                <td>${item.category.name}</td>
                <td>
                    <input type="number" id="quantity-${item.id}" min="1" value="1" style="width: 60px; margin-right: 8px;">
                    <button class="purchase-btn" onclick="purchaseItem(${item.id})">Purchase</button>
                </td>
            `;
        }

        itemsBody.appendChild(row);
    });
}

function purchaseItem(itemId) {
    //alert(`Purchased item with ID: ${itemId}`);
    const userId = sessionStorage.getItem('userId');
    const quantityInput = document.getElementById(`quantity-${itemId}`);
    const quantity = parseInt(quantityInput.value);
    console.log(`Purchasing item ${itemId} with quantity ${quantity}`);

    const payload = {
        userId: userId,
        items:[
            {
                productId:itemId,
                quantity:quantity
            }
        ]
    }

    fetch(`/eshop/orders`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(payload)
    })
        .then(res => {
            if (!res.ok) {
                // Handle failed request based on status code
                throw new Error("Failed to place order, please try again.");
            }
            return res.json();
        })
        .then(data => {
            // You can access the response data here (e.g., show an order ID)
            alert("Order placed successfully!");
            console.log("Order details:", data); // Log the order details if needed
        })
        .catch(err => {
            // Catch any errors (network issues, invalid response, etc.)
            alert(`Order failed: ${err.message}`);
            console.error("Error details:", err);
        });

}