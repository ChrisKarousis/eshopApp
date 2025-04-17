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
                <td>${item.category?.name || "N/A"}</td>
                <td><button class="purchase-btn" onclick="purchaseItem(${item.id})">Purchase</button></td>
            `;
        }

        itemsBody.appendChild(row);
    });
}

function purchaseItem(itemId) {
    alert(`Purchased item with ID: ${itemId}`);
    // You can replace this with a POST request like:
    /*
    fetch(`/eshop/orders`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ productId: itemId })
    })
    .then(res => res.json())
    .then(data => alert("Order placed!"))
    .catch(err => alert("Order failed"));
    */
}