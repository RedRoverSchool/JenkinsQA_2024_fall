<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpRequestAttachment" -->
<div>
    <#if data.method??>${data.method}<#else>GET</#if> to
    <#if data.url??>${data.url}<#else>Unknown</#if>
</div>

<#if data.body??>
    <h3>Body</h3>
    <div>
        <pre class="preformated-text">
        <#t>${data.body}
        </pre>
    </div>
</#if>

<#if (data.headers)?has_content>
    <h3>Headers</h3>
    <div>
        <#list data.headers as name, value>
            <div>
                <#if name == "Jenkins-Crumb" || name == "Authorization" || name == "Cookie">
                    ${name}: ****
                <#else>
                    ${name}: ${value!"null"}
                </#if>
            </div>
        </#list>
    </div>
</#if>


<#if (data.cookies)?has_content>
    <h3>Cookies</h3>
    <div>
        <#list data.cookies as name, value>
            <div>${name}: ${value!"null"}</div>
        </#list>
    </div>
</#if>

<#if data.curl??>
    <h3>Curl</h3>
    <div>
        <#assign maskedCurl = data.curl>
        <#if maskedCurl?contains("Authorization: Basic")>
            <#assign start = maskedCurl?index_of("Authorization: Basic") + 21>
            <#assign end = maskedCurl?index_of("'", start)>
            <#assign maskedCurl = maskedCurl[0..(start - 1)] + "*****" + maskedCurl[end..]>
        </#if>
        <#if maskedCurl?contains("Cookie:")>
            <#assign start = maskedCurl?index_of("Cookie:") + 8>
            <#assign end = maskedCurl?index_of("'", start)>
            <#assign maskedCurl = maskedCurl[0..(start - 1)] + "*****" + maskedCurl[end..]>
        </#if>
        <#if maskedCurl?contains("Jenkins-Crumb:")>
            <#assign start = maskedCurl?index_of("Jenkins-Crumb:") + 15>
            <#assign end = maskedCurl?index_of("'", start)>
            <#assign maskedCurl = maskedCurl[0..(start - 1)] + "*****" + maskedCurl[end..]>
        </#if>
        ${maskedCurl}
    </div>
</#if>


<#if (data.formParams)?has_content>
    <h3>FormParams</h3>
    <div>
        <#list data.formParams as name, value>
            <div>${name}: ${value!"null"}</div>
        </#list>
    </div>
</#if>
