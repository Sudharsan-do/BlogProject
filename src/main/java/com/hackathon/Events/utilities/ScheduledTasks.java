package com.hackathon.Events.utilities;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hackathon.Events.models.Blog;
import com.hackathon.Events.repositories.BlogRepository;

@Component
public class ScheduledTasks {
	
	@Autowired
	BlogRepository blogRepository;
	
	@Scheduled(fixedDelay = 5000)
	public void process() {
		List<Blog> l = blogRepository.findByStatus(Constants.NO);
		if(l!=null) {
			for(Blog b: l) {
				if(System.currentTimeMillis() < b.getScheduledAt().getTime()) {
					continue;
				}
				b.setStatus(Constants.YES);
				blogRepository.deleteById(b.getId().toString());
				blogRepository.save(b);
			}
		}
	}
	
}
