package org.zerock.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Setter;

@Data
@Component
public class Hotel {
	@Setter(onMethod_ = @Autowired)
	private Chef chef;
}
