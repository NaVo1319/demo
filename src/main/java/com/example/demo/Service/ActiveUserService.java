package com.example.demo.Service;

import com.example.demo.entiy.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActiveUserService {
    @Autowired
    SessionRegistry sessionRegistry;
}
