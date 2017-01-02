/*
 * Copyright (c) 2016. Ronald D. Kurr kurr@jvmguy.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kurron.example.query

import groovy.util.logging.Slf4j
import org.axonframework.eventhandling.EventHandler
import org.kurron.example.shared.ComplaintFiledEvent
import org.kurron.example.shared.ProcessingFailedEvent

@Slf4j
class EventProcessor {

    private final ComplaintQueryObjectRepository theRepository

    EventProcessor( ComplaintQueryObjectRepository repository ) {
        theRepository = repository
    }

    /**
     * Process the fact by persisting the query model.
     * @param event fact to process.
     */
    @EventHandler
    void handleEvent( ComplaintFiledEvent event  ) {
        log.info( 'Filed Complaint event {} detected.  Persisting information for use in the UI.', event.id )
        theRepository.save( new ComplaintQueryObject( id: event.id, company: event.company, description: event.description ) )
    }

    @EventHandler
    void handleEvent( ProcessingFailedEvent event  ) {
        log.info( 'Processing failure for event {} detected.  Persisting information for later analysis.', event.id )
    }

    @EventHandler
    void handleEvent( Object event  ) {
        log.warn( 'Event of type {} was ignored.', event.class.name )
    }
}
