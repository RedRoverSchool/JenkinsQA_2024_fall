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
        ${data.curl}
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
