package de.valentin.master.core.eventwriting;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import de.valentin.master.core.appservices.internalevents.ProfileChanged;
import de.valentin.master.core.appservices.internalevents.RewardGained;
import de.valentin.master.core.appservices.internalevents.TemporaryPointsAdded;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
public class ServerSentEventsHandler {

    private final Map<String, SseEmitter> emittersTempPoints = new HashMap<>();
    private final Map<String, SseEmitter> emittersPoints = new HashMap<>();
    private final Map<String, SseEmitter> emittersItems = new HashMap<>();
    private final Map<String, SseEmitter> emittersProfile = new HashMap<>();

	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/subscribe/tempPoints/{ipAddress}")
	public SseEmitter subscribeToTmpPoints(@PathVariable String ipAddress) {
        SseEmitter emitter = new SseEmitter();
        this.emittersTempPoints.put(ipAddress, emitter);

        emitter.onCompletion(() -> this.emittersTempPoints.remove(emitter));
        emitter.onTimeout(() -> {
                emitter.complete(); 
                this.emittersTempPoints.remove(emitter);
        });
        emitter.onError((e) -> this.emittersTempPoints.remove(emitter));

        return emitter;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/subscribe/points/{userId}")
	public SseEmitter subscribeToPoints(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter();
        this.emittersPoints.put(userId, emitter);

        emitter.onCompletion(() -> this.emittersPoints.remove(emitter));
        emitter.onTimeout(() -> {
                emitter.complete(); 
                this.emittersPoints.remove(emitter);
        });
        emitter.onError((e) -> this.emittersPoints.remove(emitter));

        return emitter;
	}
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/subscribe/items/{userId}")
	public SseEmitter subscribeToItems(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter();
        this.emittersItems.put(userId, emitter);

        emitter.onCompletion(() -> this.emittersItems.remove(emitter));
        emitter.onTimeout(() -> {
                emitter.complete(); 
                this.emittersItems.remove(emitter);
        });
        emitter.onError((e) -> this.emittersItems.remove(emitter));

        return emitter;
	}
	@SuppressWarnings("unlikely-arg-type")
	@GetMapping("/subscribe/profile/{userId}")
	public SseEmitter subscribeToProfileChange(@PathVariable String userId) {
        SseEmitter emitter = new SseEmitter();
        this.emittersProfile.put(userId, emitter);

        emitter.onCompletion(() -> this.emittersProfile.remove(emitter));
        emitter.onTimeout(() -> {
                emitter.complete(); 
                this.emittersProfile.remove(emitter);
        });
        emitter.onError((e) -> this.emittersProfile.remove(emitter));

        return emitter;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Async
	@EventListener
    public void onAvatarChanged(RewardGained event) {
		if (event.getAddedPoints() == 0) {
			SseEmitter emitter = this.emittersItems.get(event.getUserId().toString());
	        if (emitter != null) {
	        	try {
	        		emitter.send(event.getItemName());
	        	} catch(IOException e) {
	        		this.emittersItems.remove(emitter);
	        	}
	        }
			System.out.println("Item gained");
		} else {
			SseEmitter emitter = this.emittersPoints.get(event.getUserId().toString());
	        if (emitter != null) {
	        	try {
	        		emitter.send(event.getAddedPoints());
	        	} catch(IOException e) {
	        		this.emittersPoints.remove(emitter);
	        	}
	        }
			System.out.println("Points gained");
		}
    }

	
	@SuppressWarnings("unlikely-arg-type")
	@Async
	@EventListener
	public void onTemporaryPointsAdded(TemporaryPointsAdded event) {
		System.out.println("temporary points added.");
        SseEmitter emitter = this.emittersTempPoints.get(event.getIpAddress().toString());
        if (emitter != null) {
        	try {
        		emitter.send(event.getPoints());
        	} catch(IOException e) {
        		this.emittersTempPoints.remove(emitter);
        	}
        }
	}	
	
	@SuppressWarnings("unlikely-arg-type")
	@Async
	@EventListener
	public void profileChanged(ProfileChanged event) {
		System.out.println("Userprofile changed");
        SseEmitter emitter = this.emittersProfile.get(event.getUserId().toString());
        if (emitter != null) {
        	try { 
        		System.out.println(event.getProgress());
        		emitter.send(event.getProgress());
        	} catch(IOException e) {
        		this.emittersProfile.remove(emitter);
        	}
        }
	}
}
