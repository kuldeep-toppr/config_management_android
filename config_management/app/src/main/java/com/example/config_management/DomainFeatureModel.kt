package com.example.config_management

import com.google.gson.annotations.SerializedName

data class DomainFeatureModel(
    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int
)

data class DomainsInfo(

    @SerializedName("domains_info")
    val domainsInfo: List<DomainFeatureModel>
)

data class FeaturesInfo(

    @SerializedName("features_info")
    val featuresInfo: List<DomainFeatureModel>
)

data class DeleteDomain(

    @SerializedName("domain_info")
    val domainInfo: DomainFeatureModel
)

data class DeleteFeature(

    @SerializedName("feature_info")
    val featureInfo: DomainFeatureModel
)

//**************************************************************
//get one domain/feature details model
//**************************************************************
data class DomainDetail(

    @SerializedName("domain_info")
    val domainInfo: DomainInfo
)

data class DomainInfo(

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id : Int,

    @SerializedName("feature_id_list")
    val featureList : List<DomainFeatureModel>
)
//***************************************************************
