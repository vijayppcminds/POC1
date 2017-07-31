package com.assignment.listener;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.assignment.util.Utility;

@Component
public class ContextLoaderListener
{
    //private static final Logger logger = LoggerFactory.getLogger( ContextLoaderListener.class );

	@EventListener(ContextStartedEvent.class)
    void contextRefreshedEvent() {
		Utility.loadProperties();
    }
}
