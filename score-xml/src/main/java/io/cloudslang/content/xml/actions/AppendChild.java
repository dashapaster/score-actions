package io.cloudslang.content.xml.actions;

import com.hp.oo.sdk.content.annotations.Action;
import com.hp.oo.sdk.content.annotations.Output;
import com.hp.oo.sdk.content.annotations.Param;
import com.hp.oo.sdk.content.annotations.Response;
import com.hp.oo.sdk.content.plugin.ActionMetadata.MatchType;
import io.cloudslang.content.xml.entities.inputs.CommonInputs;
import io.cloudslang.content.xml.entities.inputs.CustomInputs;
import io.cloudslang.content.xml.services.AppendChildService;
import io.cloudslang.content.xml.utils.Constants;

import java.util.Map;

/**
 * Created by markowis on 25/02/2016.
 */
public class AppendChild {
    /**
     * Appends a child to an XML element.
     *
     * @param xmlDocument       XML string to append a child in
     * @param xPathQuery        XPATH query that results in an element or element list, where child element will be
     *                          appended
     * @param xmlElement        child element to append
     * @param secureProcessing  optional - whether to use secure processing
     * @return map of results containing success or failure text, a result message, and the modified XML
     */
    @Action(name = "Append Child",
            outputs = {
                    @Output(Constants.OutputNames.RESULT_TEXT),
                    @Output(Constants.OutputNames.RETURN_RESULT),
                    @Output(Constants.OutputNames.RESULT_XML)},
            responses = {
                    @Response(text = Constants.ResponseNames.SUCCESS, field = Constants.OutputNames.RESULT_TEXT, value = Constants.SUCCESS, matchType = MatchType.COMPARE_EQUAL),
                    @Response(text = Constants.ResponseNames.FAILURE, field = Constants.OutputNames.RESULT_TEXT, value = Constants.FAILURE, matchType = MatchType.COMPARE_EQUAL, isDefault = true, isOnFail = true)})
    public Map<String, String> execute(
            @Param(value = Constants.InputNames.XML_DOCUMENT, required = true) String xmlDocument,
            @Param(value = Constants.InputNames.XPATH_ELEMENT_QUERY, required = true) String xPathQuery,
            @Param(value = Constants.InputNames.XML_ELEMENT, required = true) String xmlElement,
            @Param(Constants.InputNames.SECURE_PROCESSING) String secureProcessing) {

        CommonInputs inputs = new CommonInputs.CommonInputsBuilder()
                .withXmlDocument(xmlDocument)
                .withXpathQuery(xPathQuery)
                .withSecureProcessing(secureProcessing)
                .build();

        CustomInputs customInputs = new CustomInputs.CustomInputsBuilder()
                .withXmlElement(xmlElement)
                .build();

        return new AppendChildService().execute(inputs, customInputs);
    }
}
