package org.example.model;

import java.io.Serializable;

public record Product(Long id, String name, Double price) implements Serializable {
}
