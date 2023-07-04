package com.hab_day.springinit.web.api;

import com.hab_day.springinit.service.ReservationService;
import com.hab_day.springinit.web.dto.ReservationSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ReserveApiController {
    private final ReservationService reservationService;

    @PostMapping("api/reserve")
    public Long save(@RequestBody ReservationSaveRequestDto requestDto){
        return reservationService.save(requestDto);
    }
}
