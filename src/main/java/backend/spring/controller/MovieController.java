package backend.spring.controller;


import backend.spring.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController  // REST API 컨트롤러로 등록
@RequestMapping("/api")  // 기본 URL 설정
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/movie")  // GET 요청 매핑
    public ResponseEntity<Map<String, String>> hello(){
        movieService.save();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from backend!");
        return ResponseEntity.ok(response);
    }
}
