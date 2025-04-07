package model;

import jakarta.persistence.*;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private double totalPrice;
    private String status;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy="order")
    private List<OrderItem> items;
}
