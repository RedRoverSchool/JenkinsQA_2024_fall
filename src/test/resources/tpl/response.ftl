<#ftl output_format="HTML">
<#-- @ftlvariable name="data" type="io.qameta.allure.attachment.http.HttpResponseAttachment" -->
<div>Status code <#if data.responseCode??>${data.responseCode} <#else>Unknown</#if></div>
<#if data.url??><div>${data.url}</div></#if>

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
        <div>${name}: ${value!"null"}</div>
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