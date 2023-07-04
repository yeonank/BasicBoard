package com.hab_day.springinit.service;

import com.hab_day.springinit.domain.reservation.ReservationRepository;
import com.hab_day.springinit.web.dto.ReservationListResponseDto;
import com.hab_day.springinit.web.dto.ReservationSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    @Transactional
    public Long save(ReservationSaveRequestDto requestDto){
        return reservationRepository.save(requestDto.toEntity()).getId();
    }//entity인 reservation을 만들어서 리포지토리에 이 엔티티를 넣는다. 생성된 엔티티의 id를 getter로 받아옴.

    @Transactional(readOnly = true)//조회만 남겨 조회 속도 개선
    public List<ReservationListResponseDto> findAllDesc(){
        return reservationRepository.findAllDesc().stream()
                .map(ReservationListResponseDto::new)
                .collect(Collectors.toList());
    }
}
