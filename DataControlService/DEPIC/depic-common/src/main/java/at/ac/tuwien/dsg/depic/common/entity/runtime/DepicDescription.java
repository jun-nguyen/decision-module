/**
 * Copyright 2013 Technische Universitat Wien (TUW), Distributed SystemsGroup
  E184.  This work was partially supported by the European Commission in terms
 * of the CELAR FP7 project (FP7-ICT-2011-8 #317790).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */



package at.ac.tuwien.dsg.depic.common.entity.runtime;

import at.ac.tuwien.dsg.depic.common.entity.primitiveaction.PrimitiveActionMetadata;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DepicDescription")
@XmlAccessorType(XmlAccessType.FIELD)
public class DepicDescription {
    
    @XmlElement(name = "DaaSDescription")
    DaaSDescription daaSDescription;
    
    @XmlElement(name = "PrimitiveActionMetadata")
    PrimitiveActionMetadata primitiveActionMetadata;

    public DepicDescription() {
    }

    public DepicDescription(DaaSDescription daaSDescription, PrimitiveActionMetadata primitiveActionMetadata) {
        this.daaSDescription = daaSDescription;
        this.primitiveActionMetadata = primitiveActionMetadata;
    }

    public DaaSDescription getDaaSDescription() {
        return daaSDescription;
    }

    public void setDaaSDescription(DaaSDescription daaSDescription) {
        this.daaSDescription = daaSDescription;
    }

    public PrimitiveActionMetadata getPrimitiveActionMetadata() {
        return primitiveActionMetadata;
    }

    public void setPrimitiveActionMetadata(PrimitiveActionMetadata primitiveActionMetadata) {
        this.primitiveActionMetadata = primitiveActionMetadata;
    }

}
