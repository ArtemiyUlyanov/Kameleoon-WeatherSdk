package me.artemiyulyanov.web;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface WebResponseParser<T> {
    T parse(String response);
}