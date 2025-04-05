package backend.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.response.CountVisitorResponseDto;
import backend.spring.service.AdminService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/visitors/count")
	public ResponseEntity<? super CountVisitorResponseDto> getVisitorsCount() {
		return adminService.getVisitorsCount();
	}

}
