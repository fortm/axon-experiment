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
package org.kurron.example

import java.util.concurrent.CompletableFuture
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping( '/complaints' )
@RestController
class ComplaintAPI {

    private final CommandGateway theGateway
    private final ComplaintQueryObjectRepository theRepository

    ComplaintAPI( CommandGateway gateway, ComplaintQueryObjectRepository repository ) {
        theGateway = gateway
        theRepository = repository
    }

    @PostMapping
    CompletableFuture<String> fileComplaint( @RequestBody Map<String, String> request ) {
        // best practice is to have the client generate the id, which means we should probably be using PUT instead
        def complaintID = UUID.randomUUID() as String
        theGateway.send( new FileComplaintCommand( complaintID, request['company' ], request['description'] ) )
    }

    @GetMapping
    List<ComplaintQueryObject> findAll() {
        theRepository.findAll()
    }

    @GetMapping( '/{id}' )
    ComplaintQueryObject find( @PathVariable String id ) {
        theRepository.findOne( id )
    }
}
