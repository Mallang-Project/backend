package backend.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.request.VisitorRequestDto;
import backend.spring.dto.response.VisitorResponseDto;
import backend.spring.service.VisitorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/visitor")
@RequiredArgsConstructor
public class VisitorController {

	private final VisitorService visitorService;

	@PostMapping
	public ResponseEntity<VisitorResponseDto> createVisitor(@RequestBody VisitorRequestDto requestDto) {
		VisitorResponseDto reponseDto = visitorService.createVisitor(requestDto);
		return ResponseEntity.ok(reponseDto);
	}
}
