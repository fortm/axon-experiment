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
@Aggregate
class Complaint {

    /**
     * Correlates the aggregate instance to the events that pertain to it.
     */
    @AggregateIdentifier
    private String complaintId

    /**
     * Processes the command.
     * @param command the proposed state change to process.
     */
    @CommandHandler
    Complaint( FileComplaintCommand command ) {
        // simulate business logic
        assert command.company

        // if the command is successfully applied, place an event into the stream -- updating the state of the aggregate
        apply( new ComplaintFiledEvent( command.id, command.company, command.description ) )
    }

    /**
     * Called when the event is generated.
     * @param event fact to process.
     */
    @EventSourcingHandler
    protected void onLoad( ComplaintFiledEvent event ) {
        // only pull out the pieces of the event that the aggregate is interested in
        complaintId = event.id
    }
}
