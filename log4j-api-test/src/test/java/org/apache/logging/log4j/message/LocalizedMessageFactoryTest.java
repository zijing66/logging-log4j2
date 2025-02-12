/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.logging.log4j.message;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests {@link LocalizedMessageFactory}.
 */
@ResourceLock(value = Resources.LOCALE, mode = ResourceAccessMode.READ)
public class LocalizedMessageFactoryTest {

    @Test
    public void testNewMessage() {
        final LocalizedMessageFactory localizedMessageFactory = new LocalizedMessageFactory(
                ResourceBundle.getBundle("MF", Locale.US));
        final Message message = localizedMessageFactory.newMessage("hello_world");
        assertEquals("Hello world.", message.getFormattedMessage());
    }
	
    @Test
    @ResourceLock(Resources.LOCALE)
    public void testNewMessageUsingBaseName() {
        final Locale defaultLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
        try {
            final LocalizedMessageFactory localizedMessageFactory = new LocalizedMessageFactory("MF");
            final String testMsg = "hello_world";
            final Message message = localizedMessageFactory.newMessage(testMsg);
            assertEquals("Hello world.", message.getFormattedMessage());
        } finally {
            Locale.setDefault(defaultLocale);
        }
    }
}
