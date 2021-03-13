package com.example.searchenginecomparer.adapter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

record Person(String name) {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    Person(@JsonProperty("name") String name) {
        this.name = name;
    }
}
