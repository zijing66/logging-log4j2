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
package org.apache.logging.log4j.plugins.validation.validators;

import java.util.Collection;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.plugins.validation.ConstraintValidator;
import org.apache.logging.log4j.plugins.validation.constraints.RequiredProperty;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

/**
 * Validator that checks that a property exists and has the correct value if a value is required.
 * <ul>
 * <li>The value {@code null}</li>
 * <li>An object of type {@link CharSequence} with length 0</li>
 * <li>An empty array</li>
 * <li>An empty {@link Collection}</li>
 * <li>An empty {@link Map}</li>
 * </ul>
 *
 * @since 3.0.0
 */
public class RequiredPropertyValidator implements ConstraintValidator<RequiredProperty> {

    private static final Logger LOGGER = StatusLogger.getLogger();

    private RequiredProperty annotation;

    @Override
    public void initialize(final RequiredProperty anAnnotation) {
        this.annotation = anAnnotation;
    }

    @Override
    public boolean isValid(final String name, final Object value) {
        String property = PropertiesUtil.getProperties().getStringProperty(annotation.name());
        if (property == null) {
            LOGGER.error("{} cannot be used. Required property {} is not defined", name, annotation.name());
            return false;
        }
        if (annotation.value().length() > 0 && !annotation.value().equalsIgnoreCase(property)) {
            LOGGER.error("{} cannot be used. Required property {} is not set to {}", name, annotation.name(),
                    annotation.value());
            return false;
        }
        return true;
    }
}
