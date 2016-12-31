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
package org.kurron.example.command

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply
import groovy.util.logging.Slf4j
import java.util.concurrent.ThreadLocalRandom
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.commandhandling.model.AggregateIdentifier
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.spring.stereotype.Aggregate
import org.kurron.example.shared.ComplaintFiledEvent

/**
 * The Command Model is used to process the incoming command, to validate it and define the outcome. Within this model, a Command Handler is
 * responsible for handling commands of a certain type and taking action based on the information contained inside it.
 *
 * An Aggregate is an entity or group of entities that is always kept in a consistent state. The Aggregate Root is the object on top of the
 * aggregate tree that is responsible for maintaining this consistent state.
 */
@Slf4j
@Aggregate
class ComplaintAggregate {

    /**
     * Correlates the aggregate instance to the events that pertain to it.
     */
    @AggregateIdentifier
    private String complaintId

    /**
     * Processes the command.  Since this is the constructor, command will cause a new aggregate to be created and stored in the repository
     * @param command the proposed state change to process.
     */
    @CommandHandler
    ComplaintAggregate( FileComplaintCommand command ) {
        def event = simulateBusinessLogic( command )

        // if the command is successfully applied, place an event into the stream -- updating the state of the aggregate
        apply( event )
    }

    private static ComplaintFiledEvent simulateBusinessLogic( FileComplaintCommand command ) {
        if( ThreadLocalRandom.current().nextBoolean() ) {
            log.debug( 'Command successfully processed.' )
        }
        else {
            throw new UnsupportedOperationException( 'Unable to process command.' )
        }
        new ComplaintFiledEvent( command.id, command.company, command.description )
    }

    /**
     * Called when the event is generated, which is only after the business logic successfully completes.
     * @param event fact to process.
     */
    @EventSourcingHandler
    protected void onLoad( ComplaintFiledEvent event ) {
        // only pull out the pieces of the event that the aggregate is interested in
        complaintId = event.id
    }
}
