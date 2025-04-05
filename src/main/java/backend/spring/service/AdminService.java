package backend.spring.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import backend.spring.dto.response.CountVisitorResponseDto;
import backend.spring.dto.response.ResponseDto;
import backend.spring.entity.Visitor;
import backend.spring.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private VisitorRepository visitorRepository;

	public ResponseEntity<? super CountVisitorResponseDto> getVisitorsCount(){
		try{
			List<Visitor> visitorList = visitorRepository.findAll();
			int totalCount = visitorList.size();

			LocalDate today = LocalDate.now();
			int todayCount = (int) visitorList.stream()
				.filter(visitor -> visitor.getCreatedAt().toLocalDate().equals(today))
				.count();

			return CountVisitorResponseDto.success(totalCount, todayCount);
		} catch(Exception e){
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
	}
}
