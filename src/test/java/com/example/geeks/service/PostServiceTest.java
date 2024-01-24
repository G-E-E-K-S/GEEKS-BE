package com.example.geeks.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
@RequiredArgsConstructor
class PostServiceTest {

    private final PostService postService;

    @Test
    void cursorBasePaging() {
        postService.cursorBasePaging(0L);
        postService.cursorBasePaging(9L);
    }
}