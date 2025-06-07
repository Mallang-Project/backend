package backend.spring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.spring.dto.request.HtmlSaveRequest;
import backend.spring.dto.response.HtmlSaveResponse;
import backend.spring.service.HtmlShareService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/html")
@RequiredArgsConstructor
public class HtmlShareController {

	private final HtmlShareService htmlShareService;

	@PostMapping("/save")
	public ResponseEntity<HtmlSaveResponse> saveHtml(@RequestBody HtmlSaveRequest request) {
		HtmlSaveResponse response = htmlShareService.saveHtml(request.html());
		return ResponseEntity.ok(response);
	}
}
