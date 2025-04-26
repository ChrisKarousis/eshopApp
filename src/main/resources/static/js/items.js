let allItems = [];
let activeCategories = new Set();
let searchTerm = "";
let minPrice = null;
let maxPrice = null;
let sortOption = "default";

document.addEventListener("DOMContentLoaded", () => {
    fetch("/eshop/products")
        .then(response => response.json())
        .then(data => {
            allItems = data;           // Save full product list
            renderItems(allItems);     // Initial render
            renderCategoryToggles();

            // Search listener
            document.getElementById("searchInput").addEventListener("input", (e) => {
                searchTerm = e.target.value.toLowerCase();
                applyFilters();
            });

            // Sort listener
            document.getElementById("sortSelect").addEventListener("change", (e) => {
                sortOption = e.target.value;
                applyFilters();
            });
        })
        .catch(error => console.error("Error fetching items:", error));
});

function applyFilters() {
    // Get price values from input
    minPrice = parseFloat(document.getElementById("minPrice").value) || null;
    maxPrice = parseFloat(document.getElementById("maxPrice").value) || null;

    let filtered = allItems.filter(item => {
        const matchesSearch = item.name.toLowerCase().includes(searchTerm);
        const matchesCategory = activeCategories.size === 0 || activeCategories.has(item.category.name);
        const matchesMinPrice = minPrice === null || item.price >= minPrice;
        const matchesMaxPrice = maxPrice === null || item.price <= maxPrice;
        return matchesSearch && matchesCategory && matchesMinPrice && matchesMaxPrice;
    });

    // Sort logic
    switch (sortOption) {
        case "name-asc":
            filtered.sort((a, b) => a.name.localeCompare(b.name));
            break;
        case "name-desc":
            filtered.sort((a, b) => b.name.localeCompare(a.name));
            break;
        case "price-asc":
            filtered.sort((a, b) => a.price - b.price);
            break;
        case "price-desc":
            filtered.sort((a, b) => b.price - a.price);
            break;
        // default: do nothing
    }

    renderItems(filtered);
}

function renderCategoryToggles() {
    const toggleContainer = document.getElementById("categoryToggles");

    fetch("/eshop/categories")
        .then(res => res.json())
        .then(categories => {
            toggleContainer.innerHTML = "";

            categories.forEach(cat => {
                const toggleId = `toggle-${cat.name.replace(/\s+/g, '-')}`;
                toggleContainer.innerHTML += `
                    <span >${cat.name}</span>
                    <label class="switch">
                        <input type="checkbox" id="${toggleId}" onchange="toggleCategory('${cat.name}')">
                        <span class="slider" style="margin-right: 15px;"></span>
                    </label>
                    
                `;
            });
        })
        .catch(err => console.error("Failed to load categories", err));

    // Load the items initially
    fetch("/eshop/products")
        .then(res => res.json())
        .then(items => {
            allItems = items; // Store items globally
            renderItems(allItems); // Render the items once fetched
        })
        .catch(err => console.error("Failed to load items", err));
}

// Function to handle toggling categories
function toggleCategory(category) {
    if (activeCategories.has(category)) {
        activeCategories.delete(category);
    } else {
        activeCategories.add(category);
    }
    applyFilters(); // Re-render items based on filter
}

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
    const userId = sessionStorage.getItem('userId');
    alert(`User with ID: ${userId}`);
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
                console.log(res);
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