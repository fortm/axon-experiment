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

import groovy.util.logging.Slf4j
import java.util.concurrent.ThreadLocalRandom
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventhandling.EventBus
import org.axonframework.eventhandling.GenericEventMessage
import org.kurron.example.shared.ProcessingFailedEvent

@Slf4j
class CommandProcessor {

    private final EventBus theEventBus

    CommandProcessor( final EventBus aEventBus ) {
        theEventBus = aEventBus
    }

    @SuppressWarnings( 'GroovyUnusedDeclaration' )
    @CommandHandler
    String simulateBusinessLogic( FileComplaintCommand command ) {
        if( ThreadLocalRandom.current().nextBoolean() ) {
            // simulate validating and successfully processing the command
            log.info( 'Command successfully processed.' )
//            apply( new ComplaintFiledEvent( command.id, command.company, command.description ) )
        }
        else {
            // simulate failing validation or processing of the command
            log.warn( 'Command unsuccessfully processed.' )
            theEventBus.publish( GenericEventMessage.asEventMessage( new ProcessingFailedEvent( command.id, command.company, command.description ) ) )
//            apply( new ProcessingFailedEvent( command.id, command.company, command.description ) )
        }
        command.id
    }
}
