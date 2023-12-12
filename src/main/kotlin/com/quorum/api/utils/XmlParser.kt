import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.quorum.api.reembolsos.models.ItemsReembolso

fun parseXmlResponse(xmlResponse: String): ItemsReembolso {
    val xmlMapper = XmlMapper().apply {
        registerKotlinModule()
    }
    return xmlMapper.readValue(xmlResponse, ItemsReembolso::class.java)
}
