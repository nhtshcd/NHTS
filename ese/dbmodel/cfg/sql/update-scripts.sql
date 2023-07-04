
--10.11.2021--
-- seed merchant menu delete script--
drop table seed_merchant;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.seedmerchant');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedMerchant.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedMerchant.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedMerchant.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedMerchant.delete');

delete from ese_ent where NAME ='profile.seedMerchant.list';
delete from ese_ent where NAME ='profile.seedMerchant.create';
delete from ese_ent where NAME ='profile.seedMerchant.update';
delete from ese_ent where NAME ='profile.seedMerchant.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;



-- AgrochemicalRegistration menu delete script--
drop table agro_chemical_registration;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='AgrochemicalRegistration');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalRegistration.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalRegistration.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalRegistration.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalRegistration.delete');

delete from ese_ent where NAME ='profile.agroChemicalRegistration.list';
delete from ese_ent where NAME ='profile.agroChemicalRegistration.create';
delete from ese_ent where NAME ='profile.agroChemicalRegistration.update';
delete from ese_ent where NAME ='profile.agroChemicalRegistration.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

 --AgrochemicalRegistration dynamicreport menu delete script--
 
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%agro%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--breederSeed menu delete script--
drop table breeder_seed;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.breederSeed');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.breederSeed.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.breederSeed.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.breederSeed.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.breederSeed.delete');

delete from ese_ent where NAME ='profile.breederSeed.list';
delete from ese_ent where NAME ='profile.breederSeed.create';
delete from ese_ent where NAME ='profile.breederSeed.update';
delete from ese_ent where NAME ='profile.breederSeed.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
           --breederSeed dynamicreport menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%breeder seed%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--varietyInclusion menu delete script--
drop table variety_inclusion;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.varietyInclusion');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.varietyInclusion.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.varietyInclusion.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.varietyInclusion.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.varietyInclusion.delete');

delete from ese_ent where NAME ='profile.varietyInclusion.list';
delete from ese_ent where NAME ='profile.varietyInclusion.create';
delete from ese_ent where NAME ='profile.varietyInclusion.update';
delete from ese_ent where NAME ='profile.varietyInclusion.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--knowledge repository menu delete script--
drop table knowledge_repository;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.knowledgeRep');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.knowledgeRep.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.knowledgeRep.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.knowledgeRep.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.knowledgeRep.delete');

delete from ese_ent where NAME ='service.knowledgeRep.list';
delete from ese_ent where NAME ='service.knowledgeRep.create';
delete from ese_ent where NAME ='service.knowledgeRep.update';
delete from ese_ent where NAME ='service.knowledgeRep.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
         --knowledge repository dynamicreport menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%knowledge%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
         




--bannedProducts menu delete script--
drop table banned_products;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.bannedProducts');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.bannedProducts.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.bannedProducts.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.bannedProducts.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.bannedProducts.delete');

delete from ese_ent where NAME ='profile.bannedProducts.list';
delete from ese_ent where NAME ='profile.bannedProducts.create';
delete from ese_ent where NAME ='profile.bannedProducts.update';
delete from ese_ent where NAME ='profile.bannedProducts.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
        --bannedProducts dynamicreport menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%banned%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--premises menu delete script--
drop table premises_reg;
drop table premises_detail;
drop table premises_part_detail;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.premises');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.premises.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.premises.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.premises.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.premises.delete');

delete from ese_ent where NAME ='profile.premises.list';
delete from ese_ent where NAME ='profile.premises.create';
delete from ese_ent where NAME ='profile.premises.update';
delete from ese_ent where NAME ='profile.premises.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
                       --premises dynamicreport menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%premises%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
                       
--fumigatorCommercialApplicator menu delete script--
drop table fumigator_commercial_applicator;


set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.fumigatorCommercialApplicator');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.fumigatorCommercialApplicator.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.fumigatorCommercialApplicator.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.fumigatorCommercialApplicator.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.fumigatorCommercialApplicator.delete');

delete from ese_ent where NAME ='profile.fumigatorCommercialApplicator.list';
delete from ese_ent where NAME ='profile.fumigatorCommercialApplicator.create';
delete from ese_ent where NAME ='profile.fumigatorCommercialApplicator.update';
delete from ese_ent where NAME ='profile.fumigatorCommercialApplicator.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
             --fumigatorCommercialApplicator dynamicreport menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%fumigator%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;


--permitApplication menu delete script--
drop table permit_application;


set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='permitApplication');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.PermitApplication.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.PermitApplication.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.PermitApplication.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.PermitApplication.delete');

delete from ese_ent where NAME ='profile.PermitApplication.list';
delete from ese_ent where NAME ='profile.PermitApplication.create';
delete from ese_ent where NAME ='profile.PermitApplication.update';
delete from ese_ent where NAME ='profile.PermitApplication.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--fieldInspection menu delete script--
drop table field_insp;
drop table field_insp_details;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.FieldInsp');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FieldInsp.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FieldInsp.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FieldInsp.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FieldInsp.delete');

delete from ese_ent where NAME ='service.FieldInsp.list';
delete from ese_ent where NAME ='service.FieldInsp.create';
delete from ese_ent where NAME ='service.FieldInsp.update';
delete from ese_ent where NAME ='service.FieldInsp.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--seedTransportOrder menu delete script--
drop table seedtransport_order;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.seedTransportOrder');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedTransportOrder.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedTransportOrder.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedTransportOrder.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedTransportOrder.delete');

delete from ese_ent where NAME ='service.seedTransportOrder.list';
delete from ese_ent where NAME ='service.seedTransportOrder.create';
delete from ese_ent where NAME ='service.seedTransportOrder.update';
delete from ese_ent where NAME ='service.seedTransportOrder.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--distribute  menu delete script--
drop table distribute;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='Distribute');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Distribute.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Distribute.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Distribute.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Distribute.delete');

delete from ese_ent where NAME ='profile.Distribute.list';
delete from ese_ent where NAME ='profile.Distribute.create';
delete from ese_ent where NAME ='profile.Distribute.update';
delete from ese_ent where NAME ='profile.Distribute.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--farm menu delete script--
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farm.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farm.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farm.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farm.delete');

delete from ese_ent where NAME ='profile.farm.list';
delete from ese_ent where NAME ='profile.farm.create';
delete from ese_ent where NAME ='profile.farm.update';
delete from ese_ent where NAME ='profile.farm.delete';

--seedCropInspRequest menu delete script--
drop table seedcrop_insprequest;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.SeedCropInspRequest');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.SeedCropInspRequest.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.SeedCropInspRequest.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.SeedCropInspRequest.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.SeedCropInspRequest.delete');

delete from ese_ent where NAME ='profile.SeedCropInspRequest.list';
delete from ese_ent where NAME ='profile.SeedCropInspRequest.create';
delete from ese_ent where NAME ='profile.SeedCropInspRequest.update';
delete from ese_ent where NAME ='profile.SeedCropInspRequest.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
        --seedCropInspRequest dynamicreport menu delete script--
 set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%seed crop%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--farmInspection menu delete script--
drop table farm_inspection;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.FarmInspections');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FarmInspections.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FarmInspections.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FarmInspections.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.FarmInspections.delete');




delete from ese_ent where NAME ='service.FarmInspections.list';
delete from ese_ent where NAME ='service.FarmInspections.create';
delete from ese_ent where NAME ='service.FarmInspections.update';
delete from ese_ent where NAME ='service.FarmInspections.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--exporterRegistration menu delete script--
drop table exporter_registration;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.ExporterRegistration');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.ExporterRegistration.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.ExporterRegistration.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.ExporterRegistration.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.ExporterRegistration.delete');

delete from ese_ent where NAME ='profile.ExporterRegistration.list';
delete from ese_ent where NAME ='profile.ExporterRegistration.create';
delete from ese_ent where NAME ='profile.ExporterRegistration.update';
delete from ese_ent where NAME ='profile.ExporterRegistration.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--finalseedInspectionRequest menu delete script--
drop table final_seed_inspection_result;
drop table final_seed_inspection_result_detail;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.finalSeedInspectionResult');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.finalSeedInspectionResult.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.finalSeedInspectionResult.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.finalSeedInspectionResult.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.finalSeedInspectionResult.delete');

delete from ese_ent where NAME ='service.finalSeedInspectionResult.list';
delete from ese_ent where NAME ='service.finalSeedInspectionResult.create';
delete from ese_ent where NAME ='service.finalSeedInspectionResult.update';
delete from ese_ent where NAME ='service.finalSeedInspectionResult.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--seedHarvest menu delete script--
drop table seed_harvest;


set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.seedHarvest');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedHarvest.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedHarvest.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedHarvest.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedHarvest.delete');

delete from ese_ent where NAME ='profile.seedHarvest.list';
delete from ese_ent where NAME ='profile.seedHarvest.create';
delete from ese_ent where NAME ='profile.seedHarvest.update';
delete from ese_ent where NAME ='profile.seedHarvest.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--routineSeedInspectionResult menu delete script--

drop table final_seed_inspection_result;
drop table final_seed_inspection_result_detail;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.routineSeedInspectionResult');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.routineSeedInspectionResult.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.routineSeedInspectionResult.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.routineSeedInspectionResult.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.routineSeedInspectionResult.delete');

delete from ese_ent where NAME ='service.routineSeedInspectionResult.list';
delete from ese_ent where NAME ='service.routineSeedInspectionResult.create';
delete from ese_ent where NAME ='service.routineSeedInspectionResult.update';
delete from ese_ent where NAME ='service.routineSeedInspectionResult.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--grower menu delete script--
drop table grower;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.grower');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.grower.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.grower.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.grower.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.grower.delete');

delete from ese_ent where NAME ='profile.grower.list';
delete from ese_ent where NAME ='profile.grower.create';
delete from ese_ent where NAME ='profile.grower.update';
delete from ese_ent where NAME ='profile.grower.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
      --grower dynamicreport menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%grower%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--seedSampleAnalysisApplication menu delete script--
drop table seed_sample_request;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.seedSampleRequest');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedSampleRequest.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedSampleRequest.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedSampleRequest.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.seedSampleRequest.delete');

delete from ese_ent where NAME ='profile.seedSampleRequest.list';
delete from ese_ent where NAME ='profile.seedSampleRequest.create';
delete from ese_ent where NAME ='profile.seedSampleRequest.update';
delete from ese_ent where NAME ='profile.seedSampleRequest.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--dealerRenewal  menu delete script--
drop table dealer_renewal;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='DealerRenewal');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.dealerRenewal.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.dealerRenewal.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.dealerRenewal.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.dealerRenewal.delete');

delete from ese_ent where NAME ='service.dealerRenewal.list';
delete from ese_ent where NAME ='service.dealerRenewal.create';
delete from ese_ent where NAME ='service.dealerRenewal.update';
delete from ese_ent where NAME ='service.dealerRenewal.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--seedMerchantRenewal menu delete script--
drop table seed_merchant_renewal;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.seedMerchantRenewal');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedMerchantRenewal.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedMerchantRenewal.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedMerchantRenewal.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedMerchantRenewal.delete');

delete from ese_ent where NAME ='service.seedMerchantRenewal.list';
delete from ese_ent where NAME ='service.seedMerchantRenewal.create';
delete from ese_ent where NAME ='service.seedMerchantRenewal.update';
delete from ese_ent where NAME ='service.seedMerchantRenewal.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--seedSampleTestResult  menu delete script--


drop table seed_sample_test_result;
drop table seed_sample_test_resultpxg;
drop table seed_sample_test_resultanalysis;
drop table seed_sample_test_resultgercapacity;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.seedSampleTestResult');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedSampleTestResult.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedSampleTestResult.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedSampleTestResult.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedSampleTestResult.delete');

delete from ese_ent where NAME ='service.seedSampleTestResult.list';
delete from ese_ent where NAME ='service.seedSampleTestResult.create';
delete from ese_ent where NAME ='service.seedSampleTestResult.update';
delete from ese_ent where NAME ='service.seedSampleTestResult.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--borderPtInsp menu delete script--
drop table border_point_inspection;


set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.borderPtInsp');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.borderPtInsp.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.borderPtInsp.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.borderPtInsp.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.borderPtInsp.delete');

delete from ese_ent where NAME ='service.borderPtInsp.list';
delete from ese_ent where NAME ='service.borderPtInsp.create';
delete from ese_ent where NAME ='service.borderPtInsp.update';
delete from ese_ent where NAME ='service.borderPtInsp.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--annualReturn  menu delete script--
drop table annual_return;
drop table annual_return_detail;


set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='AnnualReturn');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.annualReturn.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.annualReturn.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.annualReturn.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.annualReturn.delete');

delete from ese_ent where NAME ='service.annualReturn.list';
delete from ese_ent where NAME ='service.annualReturn.create';
delete from ese_ent where NAME ='service.annualReturn.update';
delete from ese_ent where NAME ='service.annualReturn.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--procurementProductEnroll menu delete script--
drop table procurement_product;



set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.procurementProduct');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.procurementProduct.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.procurementProduct.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.procurementProduct.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.procurementProduct.delete');

delete from ese_ent where NAME ='profile.procurementProduct.list';
delete from ese_ent where NAME ='profile.procurementProduct.create';
delete from ese_ent where NAME ='profile.procurementProduct.update';
delete from ese_ent where NAME ='profile.procurementProduct.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;


--importApplication menu delete script--
drop table import_application;

drop table import_application_details;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.importApplication');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.importApplication.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.importApplication.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.importApplication.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.importApplication.delete');

delete from ese_ent where NAME ='service.importApplication.list';
delete from ese_ent where NAME ='service.importApplication.create';
delete from ese_ent where NAME ='service.importApplication.update';
delete from ese_ent where NAME ='service.importApplication.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--seedDealer menu delete script--
drop table seed_dealer;


set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.seedDealer');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedDealer.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedDealer.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedDealer.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedDealer.delete');

delete from ese_ent where NAME ='service.seedDealer.list';
delete from ese_ent where NAME ='service.seedDealer.create';
delete from ese_ent where NAME ='service.seedDealer.update';
delete from ese_ent where NAME ='service.seedDealer.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--workOrder menu delete script--
drop table work_order;
drop table work_order_details;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.workOrder');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.workOrder.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.workOrder.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.workOrder.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.workOrder.delete');

delete from ese_ent where NAME ='service.workOrder.list';
delete from ese_ent where NAME ='service.workOrder.create';
delete from ese_ent where NAME ='service.workOrder.update';
delete from ese_ent where NAME ='service.workOrder.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.workOrder');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.workOrder.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.workOrder.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.workOrder.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.workOrder.delete');

delete from ese_ent where NAME ='report.workOrder.list';
delete from ese_ent where NAME ='report.workOrder.create';
delete from ese_ent where NAME ='report.workOrder.update';
delete from ese_ent where NAME ='report.workOrder.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--stopSaleOrder menu delete script--
drop table stop_sale_order;
drop table stop_sale_order_detail;
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.stopSaleOrder');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.stopSaleOrder.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.stopSaleOrder.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.stopSaleOrder.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.stopSaleOrder.delete');

delete from ese_ent where NAME ='service.stopSaleOrder.list';
delete from ese_ent where NAME ='service.stopSaleOrder.create';
delete from ese_ent where NAME ='service.stopSaleOrder.update';
delete from ese_ent where NAME ='service.stopSaleOrder.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.stopSaleOrder');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.stopSaleOrder.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.stopSaleOrder.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.stopSaleOrder.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.stopSaleOrder.delete');

delete from ese_ent where NAME ='report.stopSaleOrder.list';
delete from ese_ent where NAME ='report.stopSaleOrder.create';
delete from ese_ent where NAME ='report.stopSaleOrder.update';
delete from ese_ent where NAME ='report.stopSaleOrder.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--seedCertificationServices menu delete script--
drop table seed_certification_services;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.seedCertificationServices');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedCertificationServices.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedCertificationServices.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedCertificationServices.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.seedCertificationServices.delete');

delete from ese_ent where NAME ='service.seedCertificationServices.list';
delete from ese_ent where NAME ='service.seedCertificationServices.create';
delete from ese_ent where NAME ='service.seedCertificationServices.update';
delete from ese_ent where NAME ='service.seedCertificationServices.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
    --seedCertificationServices dynamic report menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicreport' and  des like '%seed certification%');
delete from ese_role_menu where menu_id=@ids;

delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--farmStockApprovalCertificate menu delete script--
drop table farm_stock_approval_certificate;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.farmStockApprovalCertificate');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.farmStockApprovalCertificate.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.farmStockApprovalCertificate.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.farmStockApprovalCertificate.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.farmStockApprovalCertificate.delete');

delete from ese_ent where NAME ='service.farmStockApprovalCertificate.list';
delete from ese_ent where NAME ='service.farmStockApprovalCertificate.create';
delete from ese_ent where NAME ='service.farmStockApprovalCertificate.update';
delete from ese_ent where NAME ='service.farmStockApprovalCertificate.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--transferSeedCertification menu delete script--
drop table transferseedcertification;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.transferSeedCertification');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.transferSeedCertification.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.transferSeedCertification.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.transferSeedCertification.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.transferSeedCertification.delete');

delete from ese_ent where NAME ='profile.transferSeedCertification.list';
delete from ese_ent where NAME ='profile.transferSeedCertification.create';
delete from ese_ent where NAME ='profile.transferSeedCertification.update';
delete from ese_ent where NAME ='profile.transferSeedCertification.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--auditRequest menu delete script--
drop table audit_request;

set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='service.auditRequest');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.auditRequest.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.auditRequest.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.auditRequest.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.auditRequest.delete');

delete from ese_ent where NAME ='service.auditRequest.list';
delete from ese_ent where NAME ='service.auditRequest.create';
delete from ese_ent where NAME ='service.auditRequest.update';
delete from ese_ent where NAME ='service.auditRequest.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--dynamic_report_config_table menu delete script--
SET FOREIGN_KEY_CHECKS = 0;

truncate table dynamic_report_config_detail;
truncate table dynamic_report_config_filter;

truncate table dynamic_report_config;

--agent menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.agent');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agent.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agent.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agent.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agent.delete');

delete from ese_ent where NAME ='profile.agent.list';
delete from ese_ent where NAME ='profile.agent.create';
delete from ese_ent where NAME ='profile.agent.update';
delete from ese_ent where NAME ='profile.agent.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--farmer menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.farmer');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farmer.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farmer.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farmer.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.farmer.delete');

delete from ese_ent where NAME ='profile.farmer.list';
delete from ese_ent where NAME ='profile.farmer.create';
delete from ese_ent where NAME ='profile.farmer.update';
delete from ese_ent where NAME ='profile.farmer.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--user menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.user');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.user.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.user.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.user.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.user.delete');

delete from ese_ent where NAME ='profile.user.list';
delete from ese_ent where NAME ='profile.user.create';
delete from ese_ent where NAME ='profile.user.update';
delete from ese_ent where NAME ='profile.user.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--agrochemical dealer menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.agroChemicalDealer');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalDealer.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalDealer.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalDealer.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.agroChemicalDealer.delete');

--Agrochemical dealer dynamic report menu script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.dynamicReport');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.dynamicReport.list');
delete from ese_ent where NAME ='report.dynamicReport.list';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--market intelligence menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.marketIntelligence');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.marketIntelligence.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.marketIntelligence.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.marketIntelligence.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.marketIntelligence.delete');

delete from ese_ent where NAME ='profile.marketIntelligence.list';
delete from ese_ent where NAME ='profile.marketIntelligence.create';
delete from ese_ent where NAME ='profile.marketIntelligence.update';
delete from ese_ent where NAME ='profile.marketIntelligence.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--agrochemical menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='AgroChemicalRenewal');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.agroChemicalRenewal.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.agroChemicalRenewal.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.agroChemicalRenewal.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='service.agroChemicalRenewal.delete');

delete from ese_ent where NAME ='service.agroChemicalRenewal.list';
delete from ese_ent where NAME ='service.agroChemicalRenewal.create';
delete from ese_ent where NAME ='service.agroChemicalRenewal.update';
delete from ese_ent where NAME ='service.agroChemicalRenewal.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

--Planter menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='profile.Planter');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Planter.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Planter.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Planter.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='profile.Planter.delete');

delete from ese_ent where NAME ='profile.Planter.list';
delete from ese_ent where NAME ='profile.Planter.create';
delete from ese_ent where NAME ='profile.Planter.update';
delete from ese_ent where NAME ='profile.Planter.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;
--plantingReturn menu delete script--
set FOREIGN_KEY_CHECKS=0;
SET @ids = (select id from ese_menu where NAME='report.plantingReturnReport');
delete from ese_role_menu where menu_id=@ids;
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.plantingReturnReport.list');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.plantingReturnReport.create');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.plantingReturnReport.update');
delete from ese_role_ent where ent_id=(select id from ese_ent where NAME ='report.plantingReturnReport.delete');

delete from ese_ent where NAME ='report.plantingReturnReport.list';
delete from ese_ent where NAME ='report.plantingReturnReport.create';
delete from ese_ent where NAME ='report.plantingReturnReport.update';
delete from ese_ent where NAME ='report.plantingReturnReport.delete';
delete from ese_menu_action where menu_id=@ids;
delete from ese_menu where id=@ids;

-----------------------------
--Menu script for Role
----------------------------------
create TABLE `ese_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(255) DEFAULT NULL,
  `DES` varchar(45) DEFAULT NULL,
  `IS_ADMIN` varchar(10) DEFAULT NULL,
  `NAME` varchar(45) DEFAULT NULL,
  `TYPE` int(3) DEFAULT '0',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

insert into `ese_role` VALUES
(NULL,'nhts','HCD SUPER ADMINISTRATOR','1','Admin',0),
(NULL,'nhts',NULL,'true','HCD ADMINISTRATOR',0),
(NULL,'nhts',NULL,'false','HCD INSPECTOR',3),
(NULL,'nhts',NULL,'false','HCD  TECHNICAL OFFICER',2),
(NULL,'nhts',NULL,'false','AUDITORS',4),
(NULL,'nhts',NULL,'false','EXPORTER USER',0),
(NULL,'nhts',NULL,'false','EXPORTER ADMIN',5),
(NULL,'nhts',NULL,'false','GOVERNMENT AGENCY',6),
(NULL,'nhts',NULL,'false','PARTNER',7),
(NULL,'nhts',NULL,'false','CUSTOMER CARE',-1);
-------------------------------------------------------------------
---Insert query for header logo
-------------------------------------------------------------------

insert into `assets` VALUES
(NULL,'app_logo','','Application logo','ÿ\Øÿ\à\0JFIF\0\0`\0`\0\0ÿ\Û\0C\0\n\n\n\r\rÿ\Û\0C		\r\rÿÀ\0\0g\0h\"\0ÿ\Ä\0\0\0\0\0\0\0\0\0\0\0	\nÿ\Ä\0µ\0\0\0}\0!1AQa\"q2‘¡#B±ÁR\Ñð$3br‚	\n\Z%&\'()*456789:CDEFGHIJSTUVWXYZcdefghijstuvwxyzƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹º\Â\Ã\Ä\Å\Æ\Ç\È\É\Ê\Ò\Ó\Ô\Õ\Ö\×\Ø\Ù\Ú\á\â\ã\ä\å\æ\ç\è\é\êñòóôõö÷øùúÿ\Ä\0\0\0\0\0\0\0\0	\nÿ\Ä\0µ\0\0w\0!1AQaq\"2B‘¡±Á	#3Rðbr\Ñ\n$4\á%ñ\Z&\'()*56789:CDEFGHIJSTUVWXYZcdefghijstuvwxyz‚ƒ„…†‡ˆ‰Š’“”•–—˜™š¢£¤¥¦§¨©ª²³´µ¶·¸¹º\Â\Ã\Ä\Å\Æ\Ç\È\É\Ê\Ò\Ó\Ô\Õ\Ö\×\Ø\Ù\Ú\â\ã\ä\å\æ\ç\è\é\êòóôõö÷øùúÿ\Ú\0\0\0?\0ýS¢Š(\0¢’¼\ï\Æn4½R]?NŽ\"\Ðñ$Ò‚~lg\0{W‡œgX<õœl¬›²²»o²4„%Q\Ú\'¢\Ñ\\_<xþ$šK;\È\ãŠ\íz´y\n\ëœB?­vN–ñ´’0DQ’\Ìpm–\æ¸L\Û±¸Y^¾–¶\éö·ü…(8>VIE%U³\Õ-oæ»†	\ÒY­$O\Zžcb¡€#\ÝX¡¯X\Î\én[¢¸‹\ß´Ÿ„}gVv\ï\ä@‘Ç½žR¤·##Žpk\áŒ|[\â+uOj:\r­¼°+}‚\Ò\ÖX¥‰\Øo]\Ò<…O\Ë\Ø\ÄV|ë›“©\Ã,m(\âk+]ù/?^žŒõz)ƒ¨e!”Œ‚:\ZZ\Ð\ï\n(¢€\n(¤l\à\ã­\0\r\È5\äþ:ð¢\Ú\Õ\ÅõŒ\rwÁ\Þ\É7#w\ãÒ»½RûX\ÒYž5Õ­ú\ìöL¿†0\Ã\éƒ\\\Ý\ÏÅˆ­÷,º=\äR¯\ÞI\\}s_šñ]L“C\êy¼\åI\Å\Ý>Yo\äù\\Z³\Õ&þL\é£\Ï\Í\rFü7ð]\æ“y&£¨\Ç\ä9ËŠr\Ü\ã$út\Æ>µo\Ä\Ú\çöÞ½e\á\Ûó?|²]È¼…U!Šþœþ‡\'‹<K\ãMö\ÚM¡²·\'kÈ§§\Õ\ÏOÃš\ì<\à¸<-n\ì\Ì\'½˜~öoý•}¿x™<!Œ\ÃS\Êr8Ia/z•f­Î¯¬c¢¿7\ÂÝ•––4Ÿºù\ç¿Dt÷N:×†x\×^¹øcñiu6¹±º\à€&•¡’\á\ÃGŒÛ†\Æ1\Æ26z¸\æ½\Ï5‘\â\r[ø«K{™n`F!–k9\Ú	£aÑ•×kö\Å\Éi¹\âc(N½5\ìÝ¤×©\æÿ\0ü	¢þÐŸ\nw\ØªN°›\í\"H.<œ\Ìc!0\Ãò\ë^%¬|6ø\ÍñkÀ~ð§‰ô§²žA\çŸ^š\î[\í\nŠ\ÑFwœßŽz\ã©ñþ‡\ã°\É\âM\Ø^\èð¼?\Úé³¶ñi\ì;‰8DŽ\"\à7Ë·•ð\×\Æ/x\Û\Ãv¥]\ßj\É4“ªiöQ¡¶–X<\Ã\0‘~_(¦\ænSh\"¸g\É9Zi§\Ö\Ý¯Àøœ_Õ±X‰C§\nŽ6’­5ugª»\íuð\Þ\×[ŸJ\èö0\èúM–3Ei@›ŽXª(PO\à*\íx\Ãÿ\0‡¾#ñN°.¼Yý§§Y%—\ì\Æñ¼\å2¸v³ió\æ<kµ€T\ÛrÀ\Z÷\ÔPˆª8\0b»¡\'%{XûliW§\Ì\áÊº_º\Ú¢Š+C¸(¢Š\0kc#5\å|K.µ©\Z\Ï-n#`¿ò\ÒRp\Ð:ôj_\Ù\Zõ\à8hbf_÷º\×\åôÿ\0\í[™>qnrÿ\0\ï“Á?‰ð¯\Ê8\Û[W\r\á\åg]®f¿–öüuo\Ê6Ùt\"’uC\Õ|=¢E \èö\Öh1¯\Î\ß\Þc\Ë\Ä\Õ[ÿ\0X\Ù\êb¥§º\Îc\ä\'±>¾\Õ_\Ç\Þ\">ð\ëMu<‹o¾\Ýñ\ì?…yYø½\àŸ‡¾—T^\Óumzm\é¯Ú†ö””=Jœ³có¯\Ò`°ù}\ÒVŒ ’K²Z5*F7”\Ù\è?|Q«øO\Ã\çP\Ñü1u\â\ÝG\ÌU·²µÂ”\È9w=BŒv$ŽõÁøö¤´ÖµO\ìøwPð^© Þ©0=‹¬ŸˆÇ½|\ï®üjñ†£yõÿ\0ŒoL\Ñ\Ì.b‡L\Ûmm´P	e\Æxf \äœs\\:ø\ÓX½×“Q—Y\Õ5?)”\Ê5+¶¸\Ôg\ä;ó\Æ¸uö¯Y\í)Jô\ïo4­þg\Ê\×\ÇTu\Ô\èUi+I§óø—\Þ~–\ÏW\ÖrG*$\ÐJ…nWR0AõWÀ“jÚ—\ì‡ñ\ëS¶³3K\á™\'{<œ\\\Ø\ÈIz\É\Èýœt5õGÀ?§ˆ<=LóZy\"kC!\Ë\"g\r{«q^Oûzx^?\ì\ß\nx¥cù­\î_M¹a\ÞT°\ÏÐ«\ßU\íÖ—´¤«Cu©\Ë\ÄP•\\s\n–¥tû-šô\ë\æ‘õ6‘©Zk:]¦¡c*\\Z]B³C2tt`\n‘õU\Êù\Óö!ñ„º\×\Ã\Í\êC%Ç‡¯Z\Õ	9ý\Ã|\éù\ã\è}]´\ç\í ¥\Üúl¿±øJx”­Ì¯\èú¯“\n(¢´=¢Š(‘ø¡#G\á §ž5?÷Ð®s\á2ƒ®j­ŽV%Qÿ\0}õ…umZ\ë\Â7\ÛzÇ¶OÉ†J\ã>^<U4MÀ¹·,¿PCcù\×\âyÔ½—`§S\áj)|ý¢ü\Ú;aü	]5ý¾~%Â­ðoƒµK‰µ…Æ²l¤›8T‘\à¦Ooº\Õò‹C,€[\ÜÙ­\Ø~ù{‹}=kôS\ã§Àÿ\0\nþ\Ñ?\rõø\Â\Úkñ’Pö\ÒysA*¤‘¶PA‚&¾Bø·û=k_m´ñ¤I¨xƒ\Ã\ÖñÂº¥\És \Ç\ï\Ê(¸p\0=ðzþƒŸP©8Bµ5u\ß\ç\Ôð±xx\×I¾‡”\Ú.• M2\\XÇŒ06LÅ‚±\ïÁùH\ëÁö¬C^Hm|¨\á)ôf\Ä\×O§ø³Q…–-:\âg¸sò­¸Y½¸\Íz~0Ô¾\Zør\ëZñ­ñ˜Êª¶š;…y]³Ÿ›\Ð7\0\ç€	\'\'¾.5.ý\å™Y~CS3¯6ò›vV÷ó¿õ¿s\Úÿ\0b=>\æ\ëá…®©(c_Ý¬,Aÿ\0W•–\àß‘­\Ï\Ûj\Ù&øªH\ßz+\ËW_¯˜ÁuŸ³:j\éðC\Âÿ\0ÛšL:&¢ñI#X\Ã”±£J\ìŸ\'ð’¥Iy\çšóŸ\Û\Ã^[„\Ö\ZZ·úF¥ªDª\ÊÆ¬\çõ\Ùù\×é±Š§‚Kû¿¡Á\ÄX_ì¼»†©$\Ü¢\ÚwMü7Oª¾\Ç\rû\Þ:x\ãÆ–\Ãw—=…\É\Ï÷¹ý~s_gWÇŸ°Ž’\í\â_\êX\Ì0¥­‚·bWvE_Î¾Ã­°ŸÁ_?\Ìó¸U5•S¿y\éL(¢Š\ì>¸(¢Š\0‚ú\Õ/¬§¶”f9‘£o¡¯\nSq\áO#:“>›.}3\×ñþb½î¼»\âN¡\á»\Ë\Ù#\ZÍ•®¹f6Im,»YÔŒ\ì>ø9Þ¿0ã¬¦X¼5<~J5¨»­mu½—všRK­š\êway¥\'Å»ö=2\Ò\ê+\Ëx¦…·\Å\"V#ŠK«UºŒ£21^Cð\ãâ¦™§\ÚIew;›T9‚O-‰\\žP\×ó®’Ož‡T]2OXÇ©1\0Y<ª³’z\r„\î\Ï\á^öY\Ä\Ù~?	\nµ\êÆœ\Ú÷¢\ÚM5¾ý;>\Å\ÔÀb©\Ê\Þ\ÍýÌ§©|\Ón.¦¹±K-6\âo¿<6H®\"«xsöqðž®Ã­_\Û6»ªBÁ\ášÿ\0\r,:2\Ç\Ó#±9#¶+gPø\ç\à&\ê;k\í~\Ö\Ê\â@\nCr\â7|œ+`ži/>:x#O¾K+­~\Ö\ÚöLl¶šEI[=0¤ƒ\Ïn+¢9†EÎª*\Ô\ïþ%þg]:™:tù¢žŽ\Ê\ß+¥s¼Àx\ë\à\Úÿ\0\âD^0ø´,\íe\é~ˆ\Äq\Ê\Év\Äƒ_øW\Ó\ß¾5gø/Pÿ\0„I\Z÷\Ä\'•l&ZBOR[®Þ w8\í_2|ø_£\Ç\â•\×ü«\Ø\ÙYi÷|Vww¤\Ô.³Ÿ5\Ç?\"“\Æ~ñ\Ïl\æªg]©P\ÄA÷´—ùŸœq6]š\ã}–]†\Ã\Î\Òi\Ê\\¯•[T¯ky¿DºŸNþ\Ë?\æøiðž\Â+\è\Ìz¾¨\ÇQ½Vû\Ê\î\Ô>\êA÷\Í{26F¬§!†A§\×\ÒB*Q[#\è0˜h`\èCOh¤‚Š(«:ÂŠ( ¯œ~-x;_ñ\Ä\ë»\í?\Ã\×mf\ÅÚ‘Ù™FK{d/ü¾¯(ñ¾ñO\\\ê^¹ …c[K[› –.\Ù\ã\nI9ß¼ðW›>.m•\Ó\Íðÿ\0V«\'{\ékþ)žŽ<¾««›µµÿ\0€x÷‡ô=J5!´\Û\Å ÷·q\ß\é_6x³öeø“«xƒ\ÅWöº!{kß‰\Zˆ-\í\Ú\Ú?6K(‚œL[r‚<¼dõ\Å}\ÝŸñF\ïG\Ò\î5ƒi5ÿ\0Û®´\Ý&\ï\ì\Ñ\Ç£O€\Îly\0õ\Ú\0ƒ\Îk|qµ\×4õ‡Qµ»T³<v¶ð¤\âvü…9P\îmªr\0|ün‚–\n¤\êQ\Äk+oöi÷ò=\\VtñI)\Ó\Û\Ïþ\àú÷ÁkŸµRx¹tÛ«_G\áT±ûw\Ø\áœI8¼26H¬\Ëò\ïP¡¯$ø\áû2üLñ–¹ñªûHðóK½w I§Fö±´·‰ncóŠL\Î6¶A7A_ykš\Ä\Ïi~¸…Æ‘©\Ãh\Ò\\ÁÊ¤Kz¬2ðL°²«(Q—\ÜÃ¶&\Óþ2kñ\Ú\î´]+Z\ÄK£Ef\ìÑ‡óUžIŸnHUù†\Ã\Ã\áxFXJ©G\Ã\Å^h\Ê2þm\ïò\Ú\ÏSž¦hª\Å\Å\ÓÝ·¿t\×o3Ïµ\ÝQ“y]:ñ¹þw?Ò¹+_‡~)k½3RÂº…õ¤7I3Da\Û\æ*\È	\\ \Zõh´¿am#¸\Ño-w$%\å\Æ‚Iq8\ËÈ¡€	8&—\áo~*xs\ÅV÷úÎ§\ÚkA•Å•\å\éº\"%.D‘0E ªˆ\×kd³;±b\0ƒ	À8l4”y;y%þg§þ²\ÕQ\å5÷¿ø½DþdjÀ\Ü3†\"ŸH¿tR\×\ê‡\ÇQ@Q@Q@Q@Q@Q@Q@Q@ÿ\Ù')

' ''//


-----------------------------
--Menu script for ExporterRegistration
----------------------------------
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for exporter_registration
-- ----------------------------
DROP TABLE IF EXISTS `exporter_registration`;
CREATE TABLE `exporter_registration`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `COMPANY_NAME` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `KRA_PIN` int(11) NULL DEFAULT NULL,
  `CEMAIL_ID` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `LEGAL_STATUS` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `ASSOCIATION_MEMBER` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `LICENSE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `CROP_CATEGORY` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `DOC` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `INSP_ID` int(11) NULL DEFAULT NULL,
  `EXPIRE_DATE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `IS_ACTIVE` int(11) NULL DEFAULT NULL,
  `CREATED_DATE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `APPROVAL_DATE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `CREATED_USER` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `UPDATED_DATE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `UPDATED_USER` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `VERSION` int(11) NULL DEFAULT NULL,
  `Status` int(11) NULL DEFAULT NULL,
  `CROP_HSCODE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `CROP_NAME` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `CROP_VARIETY` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `POSTAL` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `COUNTRY` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `SUB_COUNTRY` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `VILLAGE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `WARD` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `APPLICANT_NAME` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `NATIONAL_ID` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `GENDER` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `MOBILE_NO` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `AEMAIL_ID` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `Reg_No` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `WAREHOUSE` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  `DELETE_STATUS` text CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_ACTIVE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



-- ----------------------------
-- Records of country
-- ----------------------------
INSERT INTO `country` VALUES (1, 'nhts', 'C00002', '\0', 'UGANDA', 20210916053645);
INSERT INTO `country` VALUES (2, 'nhts', 'C00003', '\0', 'Manjiya', 20210918072109);
INSERT INTO `country` VALUES (3, 'nhts', 'C00004', '\0', 'India', 20211108083237);

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `LATITUDE` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LONGITUDE` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `POSTAL_CODE` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `LOCATION_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK1gda30s73dj2fj0pqfa2pb2wx`(`LOCATION_ID`) USING BTREE,
  CONSTRAINT `FK1gda30s73dj2fj0pqfa2pb2wx` FOREIGN KEY (`LOCATION_ID`) REFERENCES `location_detail` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for state
-- ----------------------------
DROP TABLE IF EXISTS `state`;
CREATE TABLE `state`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DT` datetime(0) NULL DEFAULT NULL,
  `CREATE_USER_NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `IS_ACTIVE` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LAST_UPDATE_DT` datetime(0) NULL DEFAULT NULL,
  `NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `UPDATE_USER_NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COUNTRY_ID` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  UNIQUE INDEX `UK_3ecaanptq7xjs1ml80hsupr9g`(`CODE`) USING BTREE,
  INDEX `FK4072ioalhmfn8m161jbwqwb3m`(`COUNTRY_ID`) USING BTREE,
  CONSTRAINT `FK4072ioalhmfn8m161jbwqwb3m` FOREIGN KEY (`COUNTRY_ID`) REFERENCES `country` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of state
-- ----------------------------
INSERT INTO `state` VALUES (1, 'nhts', 'S00002', NULL, NULL, '\0', NULL, 'KALANGALA', 20210916064204, NULL, 1);
INSERT INTO `state` VALUES (2, 'nhts', 'S00003', NULL, NULL, '\0', NULL, 'KAMPALA', 20210916064429, NULL, 1);
INSERT INTO `state` VALUES (3, 'nhts', 'S00004', NULL, NULL, '\0', NULL, 'Bududa', 20210918072151, NULL, 2);
INSERT INTO `state` VALUES (4, 'nhts', 'S00005', NULL, NULL, '\0', NULL, 'Tamil Nadu', 20211108083345, NULL, 3);

SET FOREIGN_KEY_CHECKS = 1;





-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES (1, 'nhts', 'M00002', NULL, NULL, 'BUJUMBA', NULL, 20210916064341, 1);
INSERT INTO `city` VALUES (2, 'nhts', 'M00003', NULL, NULL, 'BUKESA', NULL, 20210916064502, 2);
INSERT INTO `city` VALUES (3, 'nhts', 'M00004', NULL, NULL, 'Bumasata', NULL, 20210918072255, 3);
INSERT INTO `city` VALUES (4, 'nhts', 'M00005', NULL, NULL, 'Mengo', NULL, 20211102080505, 2);
INSERT INTO `city` VALUES (5, 'nhts', 'M00006', NULL, NULL, 'Nakasero', NULL, 20211102080523, 2);
INSERT INTO `city` VALUES (6, 'nhts', 'M00007', NULL, NULL, 'Buyama', NULL, 20211102105025, 1);
INSERT INTO `city` VALUES (7, 'nhts', 'M00008', NULL, NULL, 'Mulabana', NULL, 20211102105056, 1);
INSERT INTO `city` VALUES (8, 'nhts', 'M00009', NULL, NULL, 'pollachi', NULL, 20211108083436, 4);
INSERT INTO `city` VALUES (9, 'nhts', 'M00010', NULL, NULL, 'sathyamangalam', NULL, 20211108083618, 5);
INSERT INTO `city` VALUES (10, 'nhts', 'M00011', NULL, NULL, 'Kkadavu', NULL, 20211108083801, 4);
INSERT INTO `city` VALUES (11, 'nhts', 'M00012', NULL, NULL, 'Kagugube', NULL, 20211111092500, 2);
INSERT INTO `city` VALUES (12, 'nhts', 'M00013', NULL, NULL, 'Rasipuram', NULL, 20211115082604, 6);

SET FOREIGN_KEY_CHECKS = 1;


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for location_detail
-- ----------------------------
DROP TABLE IF EXISTS `location_detail`;
CREATE TABLE `location_detail`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `STATE_ID` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FK2tiiylktlu6ivvhogmw67e5l8`(`STATE_ID`) USING BTREE,
  CONSTRAINT `FK2tiiylktlu6ivvhogmw67e5l8` FOREIGN KEY (`STATE_ID`) REFERENCES `state` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of location_detail
-- ----------------------------
INSERT INTO `location_detail` VALUES (1, 'nhts', 'D00002', 'BUJUMBA', 20210916064246, 1);
INSERT INTO `location_detail` VALUES (2, 'nhts', 'D00003', 'CENTRAL DIVISION', 20210916064447, 2);
INSERT INTO `location_detail` VALUES (3, 'nhts', 'D00004', 'Bulucheke', 20210918072221, 3);
INSERT INTO `location_detail` VALUES (4, 'nhts', 'D00005', 'Coimbatore', 20211108083410, 4);
INSERT INTO `location_detail` VALUES (5, 'nhts', 'D00006', 'Erode', 20211108083557, 4);
INSERT INTO `location_detail` VALUES (6, 'nhts', 'D00007', 'Namakkal', 20211115082541, 4);

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for procurement_product
-- ----------------------------
DROP TABLE IF EXISTS `procurement_product`;
CREATE TABLE `procurement_product`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(35) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `UNIT` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `speciesName` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of procurement_product
-- ----------------------------
INSERT INTO `procurement_product` VALUES (1, 'nhts', 'CR00002', 'Cotton', 20211019093757, 'CG00377', 'Cotton');
INSERT INTO `procurement_product` VALUES (2, 'nhts', 'CR00003', 'Paddy', 20210923142347, 'CG00172', 'Paddy');
INSERT INTO `procurement_product` VALUES (4, 'nhts', 'CR00009', 'Wheat', 20211008111759, 'CG00172', 'Wheat');
INSERT INTO `procurement_product` VALUES (5, 'nhts', 'CR00010', 'ragi', 20211013084433, 'CG00172', 'Eleusine');
INSERT INTO `procurement_product` VALUES (6, 'nhts', 'CR00011', 'Tapioca', 20211019093711, 'CG00172', 'Manihot esculenta');
INSERT INTO `procurement_product` VALUES (7, 'nhts', 'CR00012', 'Jute', 20211019151718, 'CG00172', 'Corchorus capsularis');
INSERT INTO `procurement_product` VALUES (9, 'nhts', 'CR00014', 'Maize', 20211102063157, 'CG00172', 'Zea mays subsp');
INSERT INTO `procurement_product` VALUES (10, 'nhts', 'CR00015', 'Sugarcane', 20211103072334, 'CG00172', 'Saccharum officinarum');
INSERT INTO `procurement_product` VALUES (11, 'nhts', 'CR00016', 'Bell Pepper', 20211112091949, 'CG00172', 'Capsicum annuum');

SET FOREIGN_KEY_CHECKS = 1;



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for procurement_variety
-- ----------------------------
DROP TABLE IF EXISTS `procurement_variety`;
CREATE TABLE `procurement_variety`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `PROCUREMENT_PRODUCT_ID` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FKstt2sccy5ds7del4bm43cbhod`(`PROCUREMENT_PRODUCT_ID`) USING BTREE,
  CONSTRAINT `FKstt2sccy5ds7del4bm43cbhod` FOREIGN KEY (`PROCUREMENT_PRODUCT_ID`) REFERENCES `procurement_product` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of procurement_variety
-- ----------------------------
INSERT INTO `procurement_variety` VALUES (1, 'nhts', 'V00002', 'Harsha', 20211102074247, 1);
INSERT INTO `procurement_variety` VALUES (2, 'nhts', 'V00003', 'Variety 1', 20210923142406, 2);
INSERT INTO `procurement_variety` VALUES (5, 'nhts', 'V00006', 'Samba', 20211008111820, 4);
INSERT INTO `procurement_variety` VALUES (6, 'nhts', 'V00007', 'Co (Tp) 5', 20211019101626, 6);
INSERT INTO `procurement_variety` VALUES (7, 'nhts', 'V00008', 'H - 97', 20211019101736, 6);
INSERT INTO `procurement_variety` VALUES (8, 'nhts', 'V00009', 'White Jute', 20211019151732, 7);
INSERT INTO `procurement_variety` VALUES (9, 'nhts', 'V00010', 'TOSSA JUTE', 20211022143806, 7);
INSERT INTO `procurement_variety` VALUES (11, 'nhts', 'V00012', 'Fox Millets', 20211029082025, NULL);
INSERT INTO `procurement_variety` VALUES (13, 'nhts', 'V00014', 'Varun', 20211102074320, 9);
INSERT INTO `procurement_variety` VALUES (14, 'nhts', 'V00015', 'CoG 94077', 20211103072546, 10);
INSERT INTO `procurement_variety` VALUES (15, 'nhts', 'V00016', 'Yolo Wonder', 20211112092209, 11);
INSERT INTO `procurement_variety` VALUES (16, 'nhts', 'V00017', 'Eleusine coracana', 20211116120018, 5);

SET FOREIGN_KEY_CHECKS = 1;

------------------------------------------------------------------
-----Menu script for Farm
------------------------------------------------------------------

CREATE TABLE `farm` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(255) DEFAULT NULL,
  `FARMER_ID` BIGINT(20) NOT NULL,
  `FARM_CODE` varchar(255) DEFAULT NULL,
  `FARM_NAME` varchar(255) DEFAULT NULL,
  `TOTAL_LAND_HOLDING` varchar(255) DEFAULT NULL,
  `PROPOSED_PLANTING_AREA` varchar(255) DEFAULT NULL,
  `OPEN_FIELD` varchar(255) DEFAULT NULL,
  `OPEN_OTHER` varchar(255) DEFAULT NULL,
  `PROTECTED_FIELD` varchar(255) DEFAULT NULL,
  `PROTECTED_OTHER` varchar(255) DEFAULT NULL,
  `LAND_OWNERSHIP` varchar(255) DEFAULT NULL,
  `IS_ADDRESS_SAME` tinyint(4) DEFAULT NULL,
  `ADDRESS` varchar(255) DEFAULT NULL,
  `PHOTO` varchar(255) DEFAULT NULL,
  `LAND_REG_NO` varchar(255) DEFAULT NULL,
  `LAND_TOPOGRAPHY` varchar(255) DEFAULT NULL,
  `LAND_GRADIENT` varchar(255) DEFAULT NULL,
  `LAND_REG_DOCS` varchar(255) DEFAULT NULL,
  `SOIL_TYPE` varchar(255) DEFAULT NULL,
  `IRRIGATION_TYPE` varchar(255) DEFAULT NULL,
  `APPROACH_ROAD` varchar(255) DEFAULT NULL,
  `GEO_PLOTTING` varchar(255) DEFAULT NULL,
  `NO_OF_FARM_LABOURS` varchar(255) DEFAULT NULL,
  `LATITUDE` varchar(20) DEFAULT NULL,
  `LONGITUDE` varchar(20) DEFAULT NULL,
  `REVISION_NO` bigint(20) DEFAULT NULL,
  `STATUS` int(11) NOT NULL DEFAULT '0',
  `CREATED_DATE` datetime DEFAULT NULL,
  `CREATED_USER` varchar(50) DEFAULT NULL,
  `UPDATED_DATE` datetime DEFAULT NULL,
  `UPDATED_USER` varchar(50) DEFAULT NULL,
  `VERSION` int(11) DEFAULT '1',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;

--set @parent_id = 56;
--set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
--set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.farm','Farm', 'dynamicViewReportDT_list.action?id=3', 8, 114, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.farm.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.farm.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.farm.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.farm.delete');


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.farm.list'));



SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 58;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.farm','Farm', 'dynamicViewReportDT_list.action?id=4', @menu_order, @parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO `dynamic_report_config` VALUES 
(NULL, '', 'nhts', 'farm_create.action$$profile.farm.create', 'com.sourcetrace.eses.entity.Farm', 2, '1', NULL, NULL, 'Farm', '1', 'Farm', NULL, NULL),
(NULL, '', 'nhts', '', 'com.sourcetrace.eses.entity.Farm', 2, '1', NULL, NULL, 'Farm', '1', 'Farm', NULL, NULL);

INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 100, 3);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch ID', NULL, 2, NULL, 1, 0, 100, 3);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'farmCode', 0, 1, NULL, 1, NULL, 'Farm Code', NULL, 4, NULL, 1, 0, 100, 3);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'farmName', 0, 1, NULL, 1, NULL, 'Farm Name', NULL, 5, NULL, 1, 0, 100, 3);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'totalLandHolding', 0, 1, NULL, 1, NULL, 'Total Land Holding', NULL, 6, NULL, 1, 0, 100, 3);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 23, 'center', NULL, NULL, 'id', 0, 1, NULL, 1, NULL, 'Delete', NULL, 9, 'farm_delete.action?id=##profile.farm.delete', 1, 0, 100, 3);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 28, 'center', NULL, NULL, 'id', 0, 1, NULL, 1, NULL, 'Edit', NULL, 8, 'farm_update.action?id=##profile.farm.update', 1, 0, 100, 3);

INSERT INTO `dynamic_report_config_filter` VALUES (null, '0', NULL, 'deleteStatus~1~1', NULL, 'deleteStatus', NULL, NULL, 0, 1, 29);

-- -10-12-2021
Alter table farmer add column STATUS_MSG longtext;
Alter table prof add column EXPORTER_ID bigint(45)


UPDATE `eses_nhts`.`dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'branchId', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'branchId', `METHOD` = NULL, `ORDERR` = 2, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 4 WHERE `ID` = 57;
UPDATE `eses_nhts`.`dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = '5', `EXPRESSION` = NULL, `FIELD` = 'name', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Company Name', `METHOD` = '', `ORDERR` = 3, `PARAMTERS` = 'exporterRegistration_detail.action?id=', `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 4 WHERE `ID` = 58;
UPDATE `eses_nhts`.`dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'regNumber', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = NULL, `IS_FOOTER_SUM` = 1, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'KRA PIN', `METHOD` = NULL, `ORDERR` = 4, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 4 WHERE `ID` = 59;
UPDATE `eses_nhts`.`dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'tin', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = NULL, `IS_FOOTER_SUM` = 1, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Company Email', `METHOD` = NULL, `ORDERR` = 5, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 4 WHERE `ID` = 60;
UPDATE `eses_nhts`.`dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'farmToPackhouse', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = NULL, `IS_FOOTER_SUM` = 1, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Applicant Name', `METHOD` = NULL, `ORDERR` = 6, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 4 WHERE `ID` = 61;
;

ALTER TABLE `spray_field_management` 

ADD COLUMN `DOSAGE` varchar(255) NULL AFTER `DATE_OF_SPRAYING`;
ADD COLUMN `DOSAGE` varchar(255) NULL AFTER `DATE_OF_SPRAYING`;

-----------------------------------------------------13-12-2021--------------------------------------------------------

UPDATE `eses_nhts`.`locale_property` SET `lang_value` = 'Crop Category' WHERE `code` = 'crop';


INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Type of Seed', 1, 1, 4);
INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Border Crop Type', 1, 1, 5);
INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Seed Treatment Chemical Used', 1, 1, 6);
INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Fertilizer Used', 1, 1, 7);
INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Type of Fertilizer', 1, 1, 8);
INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'UOM', 1, 1, 76);
INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Mode of Application', 1, 1, 78);



ALTER TABLE `farm_crops` 
ADD COLUMN `BLOCK_ID` varchar(25) NULL AFTER `IF_OTHERS`;
ALTER TABLE `farm_crops` 
ADD COLUMN `BLOCK_NAME` varchar(255) NULL AFTER `BLOCK_ID`;
ALTER TABLE `farm_crops` 
ADD COLUMN `GRADE` varchar(255) NULL AFTER `BLOCK_NAME`;

ALTER TABLE `farm_crops` 
ADD COLUMN `LOT_NO` varchar(255) NULL AFTER `GRADE`;
ALTER TABLE `farm_crops` 
ADD COLUMN `CHEMICAL_USED` varchar(255) NULL AFTER `LOT_NO`;
ALTER TABLE `farm_crops` 
ADD COLUMN `CHEMICAL_QTY` varchar(255) NULL AFTER `CHEMICAL_USED`;


ALTER TABLE `farm_crops` 
ADD COLUMN `SEED_QTY_PLANTED` varchar(25) NULL AFTER `CHEMICAL_QTY`;
ALTER TABLE `farm_crops` 
ADD COLUMN `SEED_WEEK` varchar(255) NULL AFTER `SEED_QTY_PLANTED`;
ALTER TABLE `farm_crops` 
ADD COLUMN `FERTLISER` varchar(255) NULL AFTER `SEED_WEEK`;

ALTER TABLE `farm_crops` 
ADD COLUMN `TYPE_FERT` varchar(255) NULL AFTER `FERTLISER`;
ALTER TABLE `farm_crops` 
ADD COLUMN `FERT_QTY` varchar(255) NULL AFTER `TYPE_FERT`;
ALTER TABLE `farm_crops` 
ADD COLUMN `F_LOTNO` varchar(255) NULL AFTER `FERT_QTY`;

ALTER TABLE `farm_crops` 
ADD COLUMN `MODE_APP` varchar(255) NULL AFTER `F_LOTNO`;
ALTER TABLE `farm_crops` 
ADD COLUMN `EXP_HARVEST_WEEK` varchar(255) NULL AFTER `MODE_APP`;
ALTER TABLE `farm_crops` 
ADD COLUMN `EXP_HARVEST_QTY` varchar(255) NULL AFTER `EXP_HARVEST_WEEK`;

ALTER TABLE `farm_crops` 
ADD COLUMN `PLOTTING` varchar(255) NULL AFTER `EXP_HARVEST_QTY`;



------------------Crop Master------------------------------.

ALTER TABLE `procurement_grade` 
ADD COLUMN `NO_OF_DAYS_TO_GROW` varchar(35) NULL AFTER `PROCUREMENT_VARIETY_ID`;
ALTER TABLE `procurement_grade` 
ADD COLUMN `YIELD` double(20,5) NULL AFTER `NO_OF_DAYS_TO_GROW`;
ALTER TABLE `procurement_grade` 
ADD COLUMN `INITIAL_HARVEST_DAYS` varchar(45) NULL AFTER `YIELD`;



----------------------------------------------------------------
---Menu script for Land Preparation
----------------------------------------------------------------

CREATE TABLE `land_preparation` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(45) DEFAULT NULL,
  `EVENT_DATE` date DEFAULT NULL,
  `FARMER_ID` bigint(20) DEFAULT NULL,
  `FARM_ID` varchar(255) DEFAULT NULL,
  `BLOCK_ID` varchar(255) DEFAULT NULL,
  `CREATED_USER` varchar(45) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `UPDATED_USER` varchar(45) DEFAULT NULL,
  `UPDATED_DATE` date DEFAULT NULL,
  `STATUS` varchar(45) DEFAULT NULL,
  `STATUS_CODE` varchar(45) DEFAULT NULL,
  `VERSION` int(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `land_preparation_details` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACTIVITY` varchar(255) DEFAULT NULL,
  `ACTIVITY_MODE` varchar(255) DEFAULT NULL,
  `NO_OF_LABOURERS` varchar(255) DEFAULT NULL,
  `LAND_PREPARATION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_lpd_lp_idx_idx` (`LAND_PREPARATION_ID`),
  CONSTRAINT `fk_lpd_lp_idx` FOREIGN KEY (`LAND_PREPARATION_ID`) REFERENCES `land_preparation` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


-----------------------------------------------------------------------------
--------Menu script for Land Preparation
-----------------------------------------------------------------------------

CREATE TABLE `land_preparation` (
  `ID` bigint(11) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(45) DEFAULT NULL,
  `EVENT_DATE` date DEFAULT NULL,
  `FARM_CROPS_ID` bigint(20) DEFAULT NULL,
  `CREATED_USER` varchar(45) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `UPDATED_USER` varchar(45) DEFAULT NULL,
  `UPDATED_DATE` date DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `VERSION` int(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `land_preparation_details` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ACTIVITY` varchar(255) DEFAULT NULL,
  `ACTIVITY_MODE` varchar(255) DEFAULT NULL,
  `NO_OF_LABOURERS` varchar(255) DEFAULT NULL,
  `LAND_PREPARATION_ID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_lpd_lp_idx_idx` (`LAND_PREPARATION_ID`),
  CONSTRAINT `fk_lpd_lp_idx` FOREIGN KEY (`LAND_PREPARATION_ID`) REFERENCES `land_preparation` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 2;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.landPreparation','Land Preparation', 'dynamicViewReportDT_list.action?id=9',@menu_order,@parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.landPreparation.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.landPreparation.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.landPreparation.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.landPreparation.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.landPreparation.delete'));



SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.landPreparation','Land Preparation', 'dynamicViewReportDT_list.action?id=10', @menu_order, @parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

SET FOREIGN_KEY_CHECKS = 0;
 
INSERT INTO `dynamic_report_config` VALUES 
(NULL,'fc=farmCrops,fm=farmCrops.farm,f=farmCrops.farm.farmer',NULL,'landPreparation_create.action$$profile.landPreparation.create','com.sourcetrace.eses.entity.LandPreparation',2,'1',NULL,NULL,'Land Preparation','1','landPreparation',NULL,NULL),
(NULL,'fc=farmCrops,fm=farmCrops.farm,f=farmCrops.farm.farmer',NULL,'landPreparation_create.action$$report.landPreparation.create','com.sourcetrace.eses.entity.LandPreparation',2,'1',NULL,NULL,'Land Preparation Report','1','landPreparation',NULL,NULL);

INSERT INTO `dynamic_report_config_detail` VALUES 
(NULL,1,NULL,NULL,NULL,'id',0,1,NULL,0,NULL,'Id','',1,NULL,1,0,100,97),
(NULL,1,NULL,NULL,NULL,'branchId',0,1,NULL,1,NULL,'Branch Id','',2,NULL,1,0,100,97),
(NULL,1,NULL,NULL,NULL,'date',0,1,NULL,1,NULL,'Date of Event','',3,NULL,1,0,100,97),
(NULL,1,NULL,NULL,NULL,'f.firstName',0,1,NULL,1,NULL,'Farmer',NULL,4,NULL,1,0,100,97),
(NULL,1,NULL,NULL,NULL,'fm.farmName',0,1,NULL,1,NULL,'Farm',NULL,5,NULL,1,0,100,97),
(NULL,1,NULL,NULL,NULL,'fc.blockName',0,1,NULL,1,NULL,'Block Name',NULL,6,NULL,1,0,100,97),
(NULL,28,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Edit',NULL,7,'landPreparation_update.action?id=##profile.landPreparation.update',1,0,100,97),
(NULL,23,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Delete',NULL,8,'landPreparation_delete.action?id=##profile.landPreparation.delete',1,0,100,97),
(NULL,1,NULL,NULL,NULL,'id',0,1,NULL,0,NULL,'Id','',1,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'branchId',0,1,NULL,1,NULL,'Branch Id','',2,NULL,1,0,100,98),
(NULL,1,NULL,'5',NULL,'date',0,1,NULL,1,NULL,'Date','getGeneralDateFormat',3,'landPreparation_detail.action?id=',1,0,100,98),
(NULL,1,NULL,NULL,NULL,'f.firstName',0,1,NULL,1,NULL,'Farmer',NULL,4,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'fm.farmName',0,1,NULL,1,NULL,'Farm',NULL,5,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'fc.blockName',0,1,NULL,1,NULL,'Block Name',NULL,6,NULL,1,0,100,98); 



---------------------------------------------------------------------------
SITE PREPRATION
---------------------------------------------------------------------------



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for site_prepration
-- ----------------------------
DROP TABLE IF EXISTS `site_prepration`;
CREATE TABLE `site_prepration`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `FARM_ID` bigint(20) NULL DEFAULT NULL,
  `CROP_ID` bigint(20) NULL DEFAULT NULL,
  `ENVIRONMENTAL_ASSESSMENT` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `ENVIRONMENTAL_ASSESSMENT_REPORT` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `SOCIAL_RISK_ASSESSMENT` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `SOCIAL_RISK_ASSESSMENT_REPORT` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `SOIL_ANALYSIS` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `SOIL_ANALYSIS_REPORT` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `WATER_ANALYSIS` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `WATER_ANALYSIS_REPORT` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `CREATED_USER` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `CREATED_DATE` date NULL DEFAULT NULL,
  `UPDATED_DATE` date NULL DEFAULT NULL,
  `UPDATED_USER` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `VERSION` int(11) NULL DEFAULT 1,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `IS_ACTIVE` bigint(255) NULL DEFAULT NULL,
  `INSP_ID` int(11) NULL DEFAULT NULL,
  `STATUS_CODE` tinyint(3) NULL DEFAULT NULL,
  `STATUS_MSG` bigint(255) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;



SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 2;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.sitePrepration','Site Prepration', 'dynamicViewReportDT_list.action?id=10', 8, 114, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.sitePrepration.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.sitePrepration.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.sitePrepration.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.sitePrepration.delete');


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.sitePrepration.delete'));

--  Dynamic_report_config  Script

INSERT INTO `dynamic_report_config` VALUES (NULL, 'f=farm', NULL, 'sitePrepration_create.action$$profile.sitePrepration.create', 'com.sourcetrace.eses.entity.SitePrepration', 2, '1', NULL, NULL, 'Site Prepration', '1', 'Site Prepration', NULL, NULL);

-- dynamic_report_config_filter

INSERT INTO `dynamic_report_config_filter` VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'Status', NULL, 1, 0, 1, 10);

 dynamic_report_config_detail

INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, '5', NULL, 'f.farmName', 0, 1, NULL, 1, NULL, 'Farm', NULL, 3, 'sitePrepration_detail.action?id=', 1, 0, 100, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 8, NULL, NULL, NULL, 'environmentalAssessment', 0, 1, NULL, 1, NULL, 'Environmental Assessment', {"1":"Yes","0":"No"}, 4, NULL, 1, 0, 100, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 8, NULL, NULL, NULL, 'socialRiskAssessment', 0, 1, NULL, 1, NULL, 'Social risk assessment', {"1":"Yes","0":"No"}, 5, NULL, 1, 0, 100, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 8, NULL, NULL, NULL, 'soilAnalysis', 0, 1, NULL, 1, NULL, 'Soil analysis', {"1":"Yes","0":"No"}, 6, NULL, 1, 0, 100, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 8, NULL, NULL, NULL, 'waterAnalysisReport', 0, 1, NULL, 1, NULL, 'Water Analysis Report', {"1":"Yes","0":"No"}, 6, NULL, 1, 0, 100, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 23, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Delete', NULL, 8, 'sitePrepration_delete.action?id=##profile.sitePrepration.delete', 1, 0, 100, 10);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 28, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Edit', NULL, 7, 'sitePrepration_update.action?id=##profile.sitePrepration.update', 1, 0, 100, 10);

ALTER TABLE `scouting` 
ADD COLUMN `Name_Of_Weeds` varchar(255) NULL AFTER `Insects_Observed`;

INSERT INTO `catalogue_value` (`ID`, `BRANCH_ID`, `CODE`, `DISP_NAME`, `IS_RESERVED`, `NAME`, `REVISION_NO`, `STATUS`, `typez`) VALUES ('18', 'nhts', 'CG00431', 'Disease1', '0', 'Disease1', '20211214123656', '1', '12');
INSERT INTO `catalogue_value` (`ID`, `BRANCH_ID`, `CODE`, `DISP_NAME`, `IS_RESERVED`, `NAME`, `REVISION_NO`, `STATUS`, `typez`) VALUES ('19', 'nhts', 'CG00432', 'Disease2', '0', 'Disease2', '20211214123657', '1', '12');
------------------------------------------------------------------------
------Menu script for harvest
------------------------------------------------------------------------

CREATE TABLE `harvest` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(45) DEFAULT NULL,
  `DATE_HARVESTED` date DEFAULT NULL,
  `FARM_CROPS_ID` bigint(20) DEFAULT NULL,
  `NO_STEMS` int(11) DEFAULT NULL,
  `QUANTITY_HARVESTED` int(11) DEFAULT NULL,
  `YIELDS_HARVESTED` int(11) DEFAULT NULL,
  `EXPECTED_YIELDS_VOLUME` int(11) DEFAULT NULL,
  `NO_UNITS` int(11) DEFAULT NULL,
  `NAME_HARVESTER` varchar(255) DEFAULT NULL,
  `HARVEST_EQUIPMENT` varchar(255) DEFAULT NULL,
  `PACKING_UNIT` varchar(255) DEFAULT NULL,
  `DELIVERY_TYPE` varchar(255) DEFAULT NULL,
  `PRODUCE_ID` varchar(255) DEFAULT NULL,
  `OBSERVATION_PHI` varchar(255) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `UPDATED_DATE` date DEFAULT NULL,
  `UPDATED_USER` varchar(255) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.harvest','Harvest', 'dynamicViewReportDT_list.action?id=98',@menu_order,@parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.harvest.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.harvest.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.harvest.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.harvest.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.harvest.delete'));



SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.harvest','Harvest', 'dynamicViewReportDT_list.action?id=99', @menu_order, @parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

SET FOREIGN_KEY_CHECKS = 0;


INSERT INTO `dynamic_report_config` VALUES 
(NULL,'fc=farmCrops,fm=farmCrops.farm,f=farmCrops.farm.farmer',NULL,'harvest_create.action$$service.harvest.create','com.sourcetrace.eses.entity.Harvest',2,'1',NULL,NULL,'Harvest','1','Harvest',NULL,NULL),
(NULL,'fc=farmCrops,fm=farmCrops.farm,f=farmCrops.farm.farmer',NULL,'harvest_create.action$$report.harvest.create','com.sourcetrace.eses.entity.Harvest',2,'1',NULL,NULL,'Harvest','1','Harvest',NULL,NULL);

INSERT INTO `dynamic_report_config_detail` VALUES 
(NULL,1,NULL,NULL,NULL,'id',0,1,NULL,0,NULL,'Id','',1,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'branchId',0,1,NULL,1,NULL,'Branch Id','',2,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'date',0,1,NULL,1,NULL,'Date Harvested','getGeneralDateFormat',3,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'f.firstName',0,1,NULL,1,NULL,'Farmer',NULL,4,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'fm.farmName',0,1,NULL,1,NULL,'Farm',NULL,5,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'fc.blockName',0,1,NULL,1,NULL,'Block Name',NULL,6,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'fc.plantingId',0,1,NULL,1,NULL,'Crop Planting',NULL,7,NULL,1,0,100,98),
(NULL,28,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Edit',NULL,8,'harvest_update.action?id=##service.harvest.update',1,0,100,98),
(NULL,23,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Delete',NULL,9,'harvest_delete.action?id=##service.harvest.delete',1,0,100,98),

(NULL,1,NULL,NULL,NULL,'id',0,1,NULL,0,NULL,'Id','',1,NULL,1,0,100,99),
(NULL,1,NULL,NULL,NULL,'branchId',0,1,NULL,1,NULL,'Branch Id','',2,NULL,1,0,100,99),
(NULL,1,NULL,'5',NULL,'date',0,1,NULL,1,NULL,'Date Harvested','getGeneralDateFormat',3,'harvest_detail.action?id=',1,0,100,99),
(NULL,1,NULL,NULL,NULL,'f.firstName',0,1,NULL,1,NULL,'Farmer',NULL,4,NULL,1,0,100,99),
(NULL,1,NULL,NULL,NULL,'fm.farmName',0,1,NULL,1,NULL,'Farm',NULL,5,NULL,1,0,100,99),
(NULL,1,NULL,NULL,NULL,'fc.blockName',0,1,NULL,1,NULL,'Block Name',NULL,6,NULL,1,0,1,99),
(NULL,1,NULL,NULL,NULL,'fc.plantingId',0,1,NULL,1,NULL,'Crop Planting',NULL,7,NULL,1,0,100,99);

----------------------------------------------------------------------------------------------------------
alter table land_preparation add column status_code int(11) default 0 after status;
-----------------------------------------------------------------------------------------------------------
----Menu script for recalling
-----------------------------------------------------------------------------------------------------------
CREATE TABLE `recalling` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(255) DEFAULT NULL,
  `REC_DATE` date DEFAULT NULL,
  `REC_ENTITY` varchar(255) DEFAULT NULL,
  `VILLAGE_ID` bigint(20) DEFAULT NULL,
  `GROWER_ID` bigint(20) DEFAULT NULL,
  `EXPORTER_ID` bigint(20) DEFAULT NULL,
  `REC_CO_NAME` varchar(255) DEFAULT NULL,
  `REC_CO_CONTACT` varchar(255) DEFAULT NULL,
  `REC_NATURE` varchar(255) DEFAULT NULL,
  `REC_REASON` varchar(255) DEFAULT NULL,
  `PROCUREMENT_VARIETY_ID` bigint(20) DEFAULT NULL,
  `PRODUCT_VOLUME_DISTRIBUTION` varchar(255) DEFAULT NULL,
  `OPERATOR_NAME` varchar(255) DEFAULT NULL,
  `CONTACT_NO` varchar(255) DEFAULT NULL,
  `PO` varchar(255) DEFAULT NULL,
  `INVOICE_NO` varchar(255) DEFAULT NULL,
  `CARRIER_NO` varchar(255) DEFAULT NULL,
  `ACTION_RECALLER` varchar(255) DEFAULT NULL,
  `ACTION_STAKEHOLDERS` varchar(255) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `STATUS_CODE` int(11) DEFAULT 0,
  `VERSION` int(11) DEFAULT 1,
  `CREATED_USER` varchar(255) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `UPDATED_USER` varchar(255) DEFAULT NULL,
  `UPDATED_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


INSERT INTO `dynamic_report_config` VALUES 
(NULL,'pp=pv.procurementProduct,pv=procurementVariety',NULL,'recalling_create.action$$service.recalling.create','com.sourcetrace.eses.entity.Recalling',2,'1',NULL,NULL,'Recalling','1','Recalling',NULL,NULL),
(NULL,'pp=pv.procurementProduct,pv=procurementVariety',NULL,'recalling_create.action$$report.recalling.create','com.sourcetrace.eses.entity.Recalling',2,'1',NULL,NULL,'Recalling','1','Recalling',NULL,NULL);

INSERT INTO `dynamic_report_config_detail` VALUES 
(NULL,1,NULL,NULL,NULL,'id',0,1,NULL,0,NULL,'Id','',1,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'branchId',0,1,NULL,1,NULL,'Branch Id','',2,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'recDate',0,1,NULL,1,NULL,'Recalling Date','getGeneralDateFormat',3,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'recCoordinatorName',0,1,NULL,1,NULL,'Name of Recall Coordinator',NULL,4,NULL,1,0,100,98),
(NULL,1,NULL,NULL,NULL,'pp.name',0,1,NULL,1,NULL,'Product',NULL,5,NULL,1,0,100,98),
(NULL,28,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Edit',NULL,6,'recalling_update.action?id=##service.recalling.update',1,0,100,98),
(NULL,23,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Delete',NULL,7,'recalling_delete.action?id=##service.recalling.delete',1,0,100,98),
 


SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.recalling','Recalling', 'dynamicViewReportDT_list.action?id=98',@menu_order,@parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.recalling.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.recalling.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.recalling.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.recalling.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.recalling.delete'));



SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.recalling','Recalling', 'dynamicViewReportDT_list.action?id=99', @menu_order, @parent_id, '0', '0','0');

SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

SET FOREIGN_KEY_CHECKS = 0;


ALTER TABLE `eses_nhts`.`city_warehouse` 
ADD COLUMN `FARM_CROP_ID` int(20) NULL AFTER `REVISION_NO`,
ADD COLUMN `NAME` varchar(100) NULL AFTER `FARM_CROP_ID`,
ADD COLUMN `CODE` varchar(100) NULL AFTER `NAME`;


-----------------------------------------------------------------------------------------------
POST RECALL INSPECTION
_______________________
 

SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 2;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.postRecallInspection','Post Recall Inspection', 'dynamicViewReportDT_list.action?id=12', 1, 2, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.postRecallInspection.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.postRecallInspection.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.postRecallInspection.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.postRecallInspection.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.postRecallInspection.delete'));


INSERT INTO `dynamic_report_config` VALUES (12, NULL, NULL, 'postRecallInspection_create.action$$profile.postRecallInspection.create', 'com.sourcetrace.eses.entity.PostRecallInspection', 2, '1', NULL, NULL, 'Post Recall Inspection', '1', 'Post Recall Inspection', NULL, NULL);


INSERT INTO `dynamic_report_config_detail` VALUES (1170, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1171, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1172, 2, NULL, '5', NULL, 'inspectionDate', 0, 1, NULL, 1, NULL, 'Inspection Date', 'getGeneralDateFormat', 3, 'postRecallInspection_detail.action?id=', 1, 0, 100, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1173, 1, NULL, NULL, NULL, 'nameOfAgency', 0, 1, NULL, 1, NULL, 'Name Of Agency', NULL, 4, NULL, 1, 0, 100, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1174, 1, NULL, NULL, NULL, 'nameOfInspector', 0, 1, NULL, 1, NULL, 'Name Of Inspector', NULL, 5, NULL, 1, 0, 100, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1175, 1, NULL, NULL, NULL, 'mobileNumber', 0, 1, NULL, 1, NULL, 'Mobile Number', NULL, 6, NULL, 1, 0, 100, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1176, 1, NULL, NULL, NULL, 'operatorBeingInspected', 0, 1, NULL, 1, NULL, 'Operator Being Inspected', NULL, 6, NULL, 1, 0, 100, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1177, 23, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Delete', NULL, 8, 'postRecallInspection_delete.action?id=##profile.postRecallInspection.delete', 1, 0, 100, 12);
INSERT INTO `dynamic_report_config_detail` VALUES (1178, 28, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Edit', NULL, 7, 'postRecallInspection_update.action?id=##profile.postRecallInspection.update', 1, 0, 100, 12); 




INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00460', 'Exporter', 0, 'Exporter', NULL, 20211214123657, '1', 83);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00461', 'Grower', 0, 'Grower', NULL, 20211214123658, '1', 83);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00462', 'Marketing', 0, 'Marketing', NULL, 20211214123659, '1', 83);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00463', 'Agent', 0, 'Agent', NULL, 20211214123660, '1', 83);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00464', 'Withdrawa', 0, 'Withdrawal', NULL, 20211214123661, '1', 84);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00465', 'Disposal', 0, 'Disposal', NULL, 20211214123662, '1', 84);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00466', 'Re-labelling', 0, 'Re-labelling', NULL, 20211214123665, '1', 84);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00467', 'Caution', 0, 'Caution', NULL, 20211214123663, '1', 85);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00468', 'System Review/Upgrade', 0, 'System Review/Upgrade', NULL, 20211214123664, '1', 85);
INSERT INTO `catalogue_value` VALUES (NULL, 'nhts', 'CG00469', 'Suspension', 0, 'Suspension', NULL, 20211214123665, '1', 85);






SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 2;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.packing','Packing', 'dynamicViewReportDT_list.action?id=15', 1, 2, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE
 ese_ent.`NAME`='profile.packing.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE 
ese_ent.`NAME`='profile.packing.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.delete'));

INSERT INTO `dynamic_report_config` VALUES (15, NULL, NULL, 'packing_create.action$$profile.packing.create', 'com.sourcetrace.eses.entity.Packing', 2, '1', NULL, NULL, 'Packing', '1', 'Packing', NULL, NULL);

INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL,
 'id', NULL, 1, NULL, 1,
 0, 0, 15);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL,
 'branchId', NULL, 2, NULL, 1, 0, 100, 15);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 2, NULL, '5', NULL, 'packingDate',
 0, 1, NULL, 1, NULL, 'Packing Date', 'getGeneralDateFormat', 3, 'packing_detail.action?id=', 1, 0, 100, 15);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'packHouse', 0, 1, NULL, 1, NULL, 'Pack House', NULL, 4, NULL, 1, 0, 100, 15);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'packerName', 0, 1, NULL, 1, NULL, 'Packer Name', NULL, 5, NULL, 1, 0, 100, 15);

INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'farmer', 0, 1, NULL, 1, NULL, 'Farmer', NULL, 6, NULL, 1, 0, 100, 15);

INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'blockId', 0, 1, NULL, 1, NULL, 'Block Id', NULL, 7, NULL, 1, 0, 100, 15);

INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'productName', 0, 1, NULL, 1, NULL, 'Product Name', NULL, 8, NULL, 1, 0, 100, 15);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'hquantity', 0, 1, NULL, 1, NULL, 'Harvested Qty (unit)
', NULL, 9, NULL, 1, 0, 100, 15);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 1, NULL, NULL, NULL, 'pquantity', 0, 1, NULL, 1, NULL, 'Packed Qty (unit)
', NULL, 10, NULL, 1, 0, 100, 15);


INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 23, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Delete', NULL, 11, 'packing_delete.action?id=##profile.packing.delete', 1, 0, 100, 15);
INSERT INTO `dynamic_report_config_detail` VALUES (NULL, 28, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Edit', NULL, 12, 'packing_update.action?id=##profile.packing.update', 1, 0, 100, 15);

----------------------------------------------------------------------------------------------------------------------------------------------------------------
----Menu script for Sorting
---------------------------------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE `eses_nhts`.`sorting` (
  `ID` BIGINT NOT NULL,
  `BRANCH_ID` VARCHAR(255) NULL,
  `FARM_CROPS_ID` BIGINT NULL,
  `HARVEST_QTY` VARCHAR(255) NULL,
  `REJ_QTY` VARCHAR(255) NULL,
  `NET_QTY` VARCHAR(255) NULL,
  `STATUS` INT NULL,
  `CREATED_USER` VARCHAR(255) NULL,
  `CREATED_DATE` DATE NULL,
  `UPDATED_USER` VARCHAR(255) NULL,
  `UPDATED_DATE` DATE NULL,
  `VERSION` INT NULL DEFAULT 1,
  `STATUS_CODE` INT NULL DEFAULT 0,
  PRIMARY KEY (`ID`));
-------------------------------------------------------------------------------------------------------------------------------------

--------------------PACKHOUSE----------  
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for warehouse
-- ----------------------------
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ADDRESS` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CODE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATED_DATE` datetime NULL DEFAULT NULL,
  `CREATED_USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LOCATION` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHONE_NO` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `TYPEZ` int(11) NULL DEFAULT NULL,
  `UPDATED_DATE` datetime NULL DEFAULT NULL,
  `UPDATED_USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `WAREHOUSE_INCHARGE` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REF_WAREHOUSE_ID` bigint(20) NULL DEFAULT NULL,
  `COMMODITY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EXPORTER` bigint(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `FARM_CROP_ID` bigint(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,  
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FKip7yv9jnfpk2y8dguuc5lfnae`(`REF_WAREHOUSE_ID`) USING BTREE,
  CONSTRAINT `warehouse_ibfk_1` FOREIGN KEY (`REF_WAREHOUSE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
---------------------------------------------------

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------PACKHOUSE--INCOMING----------------
-- Table structure for warehouse_product
-- ----------------------------
DROP TABLE IF EXISTS `warehouse_product`;
CREATE TABLE `warehouse_product`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BATCH_NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COST_PRICE` double NULL DEFAULT NULL,
  `DAMAGED_STOCK` double NULL DEFAULT NULL,
  `LOT_NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REVISION_NO` bigint(20) NULL DEFAULT NULL,
  `season_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SEED` bigint(20) NULL DEFAULT NULL,
  `SEEDLING` double NULL DEFAULT NULL,
  `STOCK` double NULL DEFAULT NULL,
  `AGENT_ID` bigint(20) NULL DEFAULT NULL,
  `PRODUCT_ID` bigint(20) NULL DEFAULT NULL,
  `WAREHOUSE_ID` bigint(20) NULL DEFAULT NULL,
  `NUMBER_UNITS` int(11) NULL DEFAULT NULL,
  `RECEIVED_UNITS` int(11) NULL DEFAULT NULL,
  `TRANSFER_WEIGHT` double(10, 0) NULL DEFAULT NULL,
  `RECEIVED_WEIGHT` double(10, 0) NULL DEFAULT NULL,
  `TOTAL_WEIGHT` double(10, 0) NULL DEFAULT NULL,
  `DELIVERY_NOTE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DRIVER_CONTACT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DRIVER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RECEIPT_NO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TRUCK_TYPE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TRUCK_NO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FKgxrkagygc819nii6d19h9fjba`(`AGENT_ID`) USING BTREE,
  INDEX `FKd2gi85877r1bhpwncq40cfs9p`(`PRODUCT_ID`) USING BTREE,
  INDEX `FKkie1xhy5vt50fhuvb571k499o`(`WAREHOUSE_ID`) USING BTREE,
  CONSTRAINT `warehouse_product_ibfk_1` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_product_ibfk_2` FOREIGN KEY (`AGENT_ID`) REFERENCES `agent_prof` (`PROF_ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `warehouse_product_ibfk_3` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
------------------

UPDATE `eses_nhts`.`ese_menu` SET `FILTER` = 0, `DES` = 'Packhouse', `DIMENSION` = 0, `EXPORT_AVILABILITY` = 0, `ICON_CLASS` = NULL, `NAME` = 'profile.packhouse', `ORD` = 7, `PARENT_ID` = 2, `PRIORITY` = 0, `URL` = 'dynamicViewReportDT_list.action?id=53' WHERE `ID` = 9;

INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (NULL, 'fc=farmCrops,fm=fc.farm,f=fm.farmer,pv=fc.variety,pp=pv.procurementProduct,ex=f.exporter', NULL, 'packhouse_create.action$$profile.packhouse.create', 'com.sourcetrace.eses.entity.Packhouse', 2, '1', NULL, NULL, 'Packhouse', '1', 'Packhouse', NULL, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (NULL, 'fc=farmCrops,fm=fc.farm,f=fm.farmer,pv=fc.variety,pp=pv.procurementProduct,ex=f.exporter', NULL, 'packhouse_create.action$$report.packhouse.create', 'com.sourcetrace.eses.entity.Packhouse', 2, '1', NULL, NULL, 'Packhouse', '1', 'Packhouse', NULL, NULL);

INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 1, 0, 1, 105);


INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 108);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 108);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, '5', NULL, 'date', 0, 1, NULL, 1, NULL, 'Date Harvested', 'getGeneralDateFormat', 3, 'harvest_detail.action?id=', 1, 0, 100, 108);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'f.firstName', 0, 1, NULL, 1, NULL, 'Farmer', NULL, 4, NULL, 1, 0, 100, 108;
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'fm.farmName', 0, 1, NULL, 1, NULL, 'Farm', NULL, 5, NULL, 1, 0, 100, 108);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'fc.blockName', 0, 1, NULL, 1, NULL, 'Block Name', NULL, 6, NULL, 1, 0, 100, 108);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'fc.plantingId', 0, 1, NULL, 1, NULL, 'Crop Planting', NULL, 7, NULL, 1, 0, 100, 108);


-- -29-12-2021
alter table city_warehouse add column STOCK_TYPE bigint(45) default 1
-- - Packhouse Incoming
SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.packhouseIncoming','Packhouse Incoming', 'dynamicViewReportDT_list.action?id=111',@menu_order,@parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.packhouseIncoming.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.packhouseIncoming.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.packhouseIncoming.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.packhouseIncoming.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.packhouseIncoming.delete'));



SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.packhouseIncoming','Packhouse Incoming', 'dynamicViewReportDT_list.action?id=112', @menu_order, @parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);



SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for packhouse_incoming
-- ----------------------------
DROP TABLE IF EXISTS `packhouse_incoming`;
CREATE TABLE `packhouse_incoming`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BATCH_NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `WAREHOUSE_ID` bigint(20) NULL DEFAULT NULL,
  `OFFLOADING_DATE` date NULL DEFAULT NULL,
  `CREATED_DATE` date NULL DEFAULT NULL,
  `UPDATED_DATE` date NULL DEFAULT NULL,
  `CREATED_USER` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATED_USER` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `VERSION` int(11) NULL DEFAULT 1,
  `DELIVERY_NOTE` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `DRIVER_CONTACT` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DRIVER_NAME` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `RECEIPT_NO` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TOTAL_WEIGHT` double(20, 5) NULL DEFAULT NULL,
  `TRANSFER_WEIGHT` double(20, 5) NULL DEFAULT NULL,
  `TRUCK_NO` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TRUCK_TYPE` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NUMBER_UNITS` bigint(45) NULL DEFAULT NULL,
  `RECEIVED_UNITS` bigint(45) NULL DEFAULT NULL,
  `RECEIVED_WEIGHT` double(20, 5) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FKkie1xhy5vt50fhuvb571k499o`(`WAREHOUSE_ID`) USING BTREE,
  CONSTRAINT `packhouse_incoming_ibfk_3` FOREIGN KEY (`WAREHOUSE_ID`) REFERENCES `warehouse` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for packhouse_incoming_details
-- ----------------------------
DROP TABLE IF EXISTS `packhouse_incoming_details`;
CREATE TABLE `packhouse_incoming_details`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `PACKHOUSE_INCOMING_ID` bigint(20) NULL DEFAULT NULL,
  `FARM_CROP_ID` bigint(20) NULL DEFAULT NULL,
  `RECEIVED_WEIGHT` double(20, 5) NULL DEFAULT NULL,
  `RECEIVED_UNITS` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `NO_UNITS` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `FKnp61n6h7rv1k8vxkqflumwtqf`(`PACKHOUSE_INCOMING_ID`) USING BTREE,
  CONSTRAINT `packhouse_incoming_details_ibfk_1` FOREIGN KEY (`PACKHOUSE_INCOMING_ID`) REFERENCES `packhouse_incoming` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;





---------------------------
----Menu script for Packing
--------------------


-- packing_details script
DROP TABLE IF EXISTS `packing`;
CREATE TABLE `packing` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PACKING_DATE` date DEFAULT NULL,
  `PACKHOUSE` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PACKER_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `BATCH_NO` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATED_USER` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `UPDATED_DATE` date DEFAULT NULL,
  `UPDATED_USER` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `VERSION` int(11) DEFAULT '1',
  `STATUS` int(11) DEFAULT NULL,
  `TOTAL_WT` double(25,5) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;
-- ----------------------------
-- Table structure for packing
-- ----------------------------
DROP TABLE IF EXISTS `packing_detail`;
CREATE TABLE `packing_detail` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Farmer` bigint(20) DEFAULT NULL,
  `Farm` bigint(20) DEFAULT NULL,
  `block_Id` bigint(20) DEFAULT NULL,
  `batch_No` bigint(20) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `price` varchar(50) DEFAULT NULL,
  `best_Before` date DEFAULT NULL,
  `PACKING_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
SET FOREIGN_KEY_CHECKS = 1;



SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 2;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.packing','Packing', 'dynamicViewReportDT_list.action?id=15', 1, 2, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'profile.packing.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE
 ese_ent.`NAME`='profile.packing.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE 
ese_ent.`NAME`='profile.packing.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='profile.packing.delete'));



INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (15, 'ph=packHouse,ex=ph.exporter', NULL, 'packing_create.action$$profile.packing.create', 'com.sourcetrace.eses.entity.Packing', 2, '1', NULL, NULL, 'Packing', '1', 'Packing', NULL, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (16, 'ph=packHouse,ex=ph.exporter', NULL, NULL, 'com.sourcetrace.eses.entity.Packing', 2, '1', NULL, NULL, 'Packing', '1', 'Packing', NULL, NULL);


INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1314, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1315, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1316, 2, NULL, '5', NULL, 'packingDate', 0, 1, NULL, 1, NULL, 'Packing Date', 'getGeneralDateFormat', 3, 'packing_detail.action?id=', 1, 0, 100, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1317, 1, NULL, NULL, NULL, 'ph.name', 0, 1, NULL, 1, NULL, 'Pack House', NULL, 4, NULL, 1, 0, 100, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1318, 1, NULL, NULL, NULL, 'packerName', 0, 1, NULL, 1, NULL, 'Packer Name', NULL, 5, NULL, 1, 0, 100, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1319, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Lot No', NULL, 7, NULL, 1, 0, 100, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1320, 23, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Delete', NULL, 11, 'packing_delete.action?id=##profile.packing.delete', 1, 0, 100, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1321, 28, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Edit', NULL, 12, 'packing_update.action?id=##profile.packing.update', 1, 0, 100, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1322, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 16);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1323, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 16);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1324, 2, NULL, '5', NULL, 'packingDate', 0, 1, NULL, 1, NULL, 'Packing Date', 'getGeneralDateFormat', 3, 'packing_detail.action?id=', 1, 0, 100, 16);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1325, 1, NULL, NULL, NULL, 'packHouse', 0, 1, NULL, 1, NULL, 'Pack House', NULL, 4, NULL, 1, 0, 100, 16);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1326, 1, NULL, NULL, NULL, 'packerName', 0, 1, NULL, 1, NULL, 'Packer Name', NULL, 5, NULL, 1, 0, 100, 16);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1327, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Lot No', NULL, 7, NULL, 1, 0, 100, 16);



INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (180, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 1, 0, 1, 15);
INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (181, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 1, 0, 1, 16);



SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'profile.packing','Packing', 'dynamicViewReportDT_list.action?id=16', @menu_order, @parent_id, '0', '0','0');

SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

SET FOREIGN_KEY_CHECKS = 0;


---------------------------------------
--Menu script for Shipment
---------------------------------------

CREATE TABLE `shipment` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(255) DEFAULT NULL,
  `SHIPMENT_DATE` date DEFAULT NULL,
  `PACKHOUSE_ID` bigint(20) DEFAULT NULL,
  `CUSTOMER_ID` bigint(20) DEFAULT NULL,
  `PRODUCE_CONSIGNMENT_NO` varchar(255) DEFAULT NULL,
  `KENYA_TRACE_CODE` varchar(255) DEFAULT NULL,
  `QR_CODE` varchar(255) DEFAULT NULL,
  `TOTAL_SHIPMENT_QTY` int(11) DEFAULT NULL,
  `CREATED_DATE` date DEFAULT NULL,
  `CREATED_USER` varchar(45) DEFAULT NULL,
  `UPDATED_DATE` date DEFAULT NULL,
  `UPDATED_USER` varchar(45) DEFAULT NULL,
  `STATUS` int(11) DEFAULT '1',
  `VERSION` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `shipment_details` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CITY_WAREHOUSE_ID` bigint(20) DEFAULT NULL,
  `PACKING_UNIT` varchar(255) DEFAULT NULL,
  `PACKING_QTY` varchar(255) DEFAULT NULL,
  `SHIPMENT_ID` bigint(255) DEFAULT NULL,
  `RECALLING_STATUS` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.shipment','Shipment', 'dynamicViewReportDT_list.action?id=115',@menu_order,@parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.shipment.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.shipment.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.shipment.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.shipment.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.delete'));


INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.shipment.delete'));



SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'service.shipment','Shipment', 'dynamicViewReportDT_list.action?id=116', @menu_order, @parent_id, '0', '0','0');


SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

SET FOREIGN_KEY_CHECKS = 0;


INSERT INTO `dynamic_report_config` VALUES 
(NULL,'p=packhouse,ex=p.exporter,c=customer',NULL,'shipment_create.action$$service.shipment.create','com.sourcetrace.eses.entity.Shipment',2,'1',NULL,NULL,'Shipment','1','Shipment',NULL,NULL),
(NULL,'p=packhouse,ex=p.exporter,c=customer',NULL,'shipment_create.action$$report.shipment.create','com.sourcetrace.eses.entity.Shipment',2,'1',NULL,NULL,'Shipment','1','Shipment',NULL,NULL);

INSERT INTO `dynamic_report_config_detail` VALUES 
(NULL,1,NULL,NULL,NULL,'id',0,1,NULL,0,NULL,'Id','',1,NULL,1,0,100,115),
(NULL,1,NULL,NULL,NULL,'branchId',0,1,NULL,1,NULL,'Branch Id','',2,NULL,1,0,100,115),
(NULL,1,NULL,NULL,NULL,'shipmentDate',0,1,NULL,1,NULL,'Shipment Date',NULL,3,NULL,1,0,100,115),
(NULL,1,NULL,NULL,NULL,'p.name',0,1,NULL,1,NULL,'Packhouse',NULL,4,NULL,1,0,100,115),
(NULL,1,NULL,NULL,NULL,'c.customerName',0,1,NULL,1,NULL,'Customer',NULL,5,NULL,1,0,100,115),
(NULL,1,NULL,NULL,NULL,'pConsignmentNo',0,1,NULL,1,NULL,'Produce Consignment Number',NULL,6,NULL,1,0,100,115),
(NULL,28,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Edit',NULL,7,'shipment_update.action?id=##service.shipment.update',1,0,100,115),
(NULL,23,'center',NULL,NULL,'id',0,1,NULL,1,NULL,'Delete',NULL,8,'shipment_delete.action?id=##service.shipment.delete',1,0,100,115),

(NULL,1,NULL,NULL,NULL,'id',0,1,NULL,0,NULL,'Id','',1,NULL,1,0,100,116),
(NULL,1,NULL,NULL,NULL,'branchId',0,1,NULL,1,NULL,'Branch Id','',2,NULL,1,0,100,116),
(NULL,1,NULL,5,NULL,'shipmentDate',0,1,NULL,1,NULL,'Shipment Date',NULL,3,NULL,1,0,100,116),
(NULL,1,NULL,NULL,NULL,'p.name',0,1,NULL,1,NULL,'Packhouse',NULL,4,NULL,1,0,100,116),
(NULL,1,NULL,NULL,NULL,'c.customerName',0,1,NULL,1,NULL,'Customer',NULL,5,NULL,1,0,100,116),
(NULL,1,NULL,NULL,NULL,'pConsignmentNo',0,1,NULL,1,NULL,'Produce Consignment Number',NULL,6,NULL,1,0,100,116);

INSERT INTO `eses_nhts`.`dynamic_report_config_filter`
(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) 
VALUES 
(NULL, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 1, 0, 1, 115);


--------------Traceability Menu Script----------------

SET FOREIGN_KEY_CHECKS = 1;

set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `eses_nhts`.`ese_menu`(`ID`, `FILTER`, `DES`, `DIMENSION`, `EXPORT_AVILABILITY`, `ICON_CLASS`, `NAME`, `ORD`, `PARENT_ID`, `PRIORITY`, `URL`) VALUES (NULL, 0, 'Traceability View Report', NULL, 0, NULL, 'report.traceabilityView.list', 15, 4, 0, 'traceabilityViewReport_list.action');



SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 8);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 9);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.traceabilityView.list');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.traceabilityView.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.traceabilityView.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('7', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.traceabilityView.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('11', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.traceabilityView.list'));


SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 38, NULL, NULL, NULL, 'pConsignmentNo', 0, 1, NULL, 1, NULL, 'QR Code', NULL, 9, NULL, 1, 0, 100, 117);

INSERT INTO `eses_nhts`.`locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'report.traceabilityView', 'en', 'Traceability View Report');

---For plotting in planting-----
ALTER TABLE `farm_crops` 
ADD COLUMN `LATITUDE` VARCHAR(45) NULL AFTER `MSG_NO`,
ADD COLUMN `LONGITUDE` VARCHAR(45) NULL AFTER `LATITUDE`;
--------------------------------

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1427, 39, NULL, NULL, NULL, 'pConsignmentNo', 0, 1, NULL, 1, NULL, 'Incoming Receipt', NULL, 10, NULL, 1, 0, 100, 117);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1428, 40, NULL, NULL, NULL, 'pConsignmentNo', 0, 1, NULL, 1, NULL, 'Outcoming Receipt', NULL, 11, NULL, 1, 0, 100, 117);

---For PCBP menu -----
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
        VALUES (NULL, 'service.Pcbp ','PCBP ', 'dynamicViewReportDT_list.action?id=119', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.Pcbp .list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.Pcbp .create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.Pcbp .update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.Pcbp .delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.Pcbp .delete'));


INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (NULL, 'crv=cropvariety,crn=crv.procurementVariety', NULL, 'pcbp_create.action$$service.Pcbp .create', 'com.sourcetrace.eses.entity.Pcbp', 2, '1', NULL, NULL, 'PCBP', '1', 'PCBP', NULL, NULL);


INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, '5', NULL, 'registrationNo', 0, 1, NULL, 1, NULL, 'Registration No', NULL, 3, 'pcbp_detail.action?id=', 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 23, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Delete', NULL, 19, 'pcbp_delete.action?id=##service.Pcbp .delete', 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 28, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Edit', NULL, 18, 'pcbp_update.action?id=##service.Pcbp .update', 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'crn.name', 0, 1, NULL, 1, NULL, 'Crop Name', NULL, 5, NULL, 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'crv.name', 0, 1, NULL, 1, NULL, 'Crop Variety', NULL, 6, NULL, 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'phiIn', 0, 1, NULL, 1, NULL, 'Phi (In Days)', NULL, 9, NULL, 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'dosage', 0, 1, NULL, 1, NULL, 'Dosage', NULL, 7, NULL, 1, 0, 100, 119);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 2, NULL, NULL, NULL, 'uom', 0, 1, NULL, 1, NULL, 'UOM', 'getCatalgueNameByCode', 8, NULL, 1, 0, 100, 119);


INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'Status', NULL, 1, 0, 1, 119);

--------Table Structure for the PBCP menu -----------
DROP TABLE IF EXISTS `pcbp`;
CREATE TABLE `pcbp`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CROP_VARIETY` bigint(50) NULL DEFAULT NULL,
  `TRADER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `REGISTRATION_NO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ACTIVE_ING` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MANUFACTURER_REG` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AGENT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `PHIIN` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DOSAGE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UOM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `STATUS_CODE` int(11) NULL DEFAULT 0,
  `VERSION` int(11) NULL DEFAULT 1,
  `CREATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATED_DATE` date NULL DEFAULT NULL,
  `UPDATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATED_DATE` date NULL DEFAULT NULL,
  `CHEMICAL_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


------------------------SMS--------------------------------------
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'From STS: ', 'SMS_TEMPLATE');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'ravisankar@sourcetrace.com', 'SMS_USER_NAME');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, '1', 'SMS_TEST_STATUS');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, '1', 'SMS_ALTERS');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'https://api.txtlocal.com/send/?', 'SMS_GATEWAY_URL');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'https://api.txtlocal.com/send/?', 'SMS_BULK_GATEWAY_URL');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'http://fastalerts.in/api/v1/<!-token-!>/sms/<!-uuid-!>/status.json', 'SMS_STATUS_URL');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'vcq07onAi0c-iOGEkpVs9yYKTI4v8xbrifWr9M7Nio', 'SMS_TOKEN');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'TNPLPM', 'SMS_SENDER_ID');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'TEXT LOCAL IN', 'SMS_ROUTE');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, '0', 'SMS_UNICODE');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, '0', 'SMS_FLASH');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'You are successfully Registered', 'SMS_MESSAGE');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'Your payment is approved for <cash_mode>  of <txn_amt> Euros and your available balance is <bal_amt> ', 'SMS_MESSAGE_PAYMENT');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'https://www.thetexting.com/rest/sms/json', 'US_SMS_URL');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'wm6jxtv5bnpck0i', 'US_SMS_API_KEY');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'yhjfc65ofq3p6dw', 'US_SMS_API_SECRET');
INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'test', 'US_SMS_FROM');


ALTER TABLE `eses_nhts`.`spray_field_management` 
ADD COLUMN `PCBP_ID` bigint(50) NULL AFTER `MSG_NO`;

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1114, 1, NULL, NULL, NULL, 'pc.tradeName', 0, 1, NULL, 1, NULL, 'Trade name and type of formulation', '', 13, NULL, 1, 0, 100, 94);

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1116, 1, NULL, NULL, NULL, 'uom', 0, 1, NULL, 1, NULL, 'UOM', '', 15, NULL, 1, 0, 100, 94);

INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (94, 'fc=farmCrops,fm=fc.farm,f=fm.farmer,pv=fc.variety,pg=fc.grade,pp=pv.procurementProduct,ex=f.exporter,pc=pcbp', NULL, 'sprayAndFieldManagement_create.action$$service.sprayAndFieldManagement.create', 'com.sourcetrace.eses.entity.SprayAndFieldManagement', 2, '1', NULL, NULL, 'Spraying', '1', 'SprayAndFieldManagementReport', NULL, NULL);

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1156, 1, NULL, NULL, NULL, 'pc.tradeName', 0, 1, NULL, 1, NULL, 'Trade name and type of formulation', '', 13, NULL, 1, 0, 100, 93);

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1158, 1, NULL, NULL, NULL, 'uom', 0, 1, NULL, 1, NULL, 'UOM', '', 15, NULL, 1, 0, 100, 93);

INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (93, 'fc=farmCrops,fm=fc.farm,f=fm.farmer,pv=fc.variety,pg=fc.grade,pp=pv.procurementProduct,ex=f.exporter,pc=pcbp', NULL, NULL, 'com.sourcetrace.eses.entity.SprayAndFieldManagement', 2, '1', NULL, NULL, 'Spraying', '1', 'SprayAndFieldManagementReport', NULL, NULL);

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1403, 2, NULL, NULL, NULL, 'operatorBeingInspected', 0, 1, NULL, 1, NULL, 'Operator Being Inspected', 'getCatalgueNameByCode', 9, NULL, 1, 0, 100, 13);

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (1395, 2, NULL, NULL, NULL, 'operatorBeingInspected', 0, 1, NULL, 1, NULL, 'Operator Being Inspected', 'getCatalgueNameByCode', 9, NULL, 1, 0, 100, 12);

-- 01-02-2022 
INSERT INTO `pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (4, '20', 'AGE');
INSERT INTO `pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (4, '16', 'PASSWORD_MAX_LENGTH');
INSERT INTO `pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (4, '8', 'PASSWORD_MIN_LENGTH');
INSERT INTO `pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (4, '40', 'REMINDER_DAYS');


SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for password_history
-- ----------------------------
DROP TABLE IF EXISTS `password_history`;
CREATE TABLE `password_history`  (
  `ID` int(20) NOT NULL AUTO_INCREMENT,
  `branch_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `createdDate` datetime NULL DEFAULT NULL,
  `password` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `type` varchar(3) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `REF_ID` bigint(45) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `sms_history`;
CREATE TABLE `sms_history`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATE_DT` datetime NULL DEFAULT NULL,
  `CREATE_USER` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creationInfo` varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LAST_UPDATE_USER` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `MESSAGE` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `receiverMobNo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SENDER_MOB_NO` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SMS_ROUTE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SMS_TYPE` int(11) NULL DEFAULT NULL,
  `STATUS` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STATUS_MSG` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `statusZ` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATE_DT` datetime NULL DEFAULT NULL,
  `UUID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `response` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


-- buyer dynamic table scripts

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`) VALUES (8, NULL, NULL, 'customer_create.action$$profile.customer.create', 'com.sourcetrace.eses.entity.Customer', 2, '1', NULL, NULL, 'Buyer', '1', 'Buyer', NULL, NULL);


INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 8);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 8);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, '5', NULL, 'customerId', 0, 1, NULL, 1, NULL, 'Code', NULL, 3, 'customer_detail.action?id=', 1, 0, 100, 8);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 28, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Edit', NULL, 15, 'customer_update.action?id=##profile.customer.update', 1, 0, 100, 8);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'customerName', 0, 1, NULL, 1, NULL, 'Name', '', 4, NULL, 1, 0, 100, 8);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'personName', 0, 1, NULL, 1, NULL, 'Contact Person', '', 5, NULL, 1, 0, 100, 8);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'mobileNo', 0, 1, NULL, 1, NULL, 'Contact Number', '', 6, NULL, 1, 0, 100, 8);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`) VALUES (NULL, 1, NULL, NULL, NULL, 'emailId', 0, 1, NULL, 1, NULL, 'Email Address', '', 7, NULL, 1, 0, 100, 8);

-- Dashboard Table 

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dashboard
-- ----------------------------
DROP TABLE IF EXISTS `dashboard`;
CREATE TABLE `dashboard`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CHART_DIV_ID` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `CHART_TYPE` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CHART_TITLE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CHART_SUB_TITLE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `X_AXIS_CATEGORY` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `Y_AXIS_TITLE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `X_AXIS_TITLE` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TOOLTIP_SUFFIX` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LEGEND_ENABLE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `STACK_LEGEND_ENABLE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SERIES_DATA` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `SERIES_NAME` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `COLOR` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `DATALABEL_FORMAT` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TOOLTIP_POINT_FORMAT` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CHART_QUERY` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `STATUS` int(10) NULL DEFAULT 1,
  `branch_id` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_date` datetime NULL DEFAULT NULL,
  `created_user` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updated_date` datetime NULL DEFAULT NULL,
  `updated_user` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `version` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CHART_ORDER` int(40) NULL DEFAULT NULL,
  `DATE_FILTER` int(10) NULL DEFAULT 1,
  `DATE_FILTER_FIELD` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EXPORTER_FILTER_FIELD` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `GROUP_BY_FIELD` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ORDER_BY_FIELD` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TABS` int(20) NULL DEFAULT NULL,
  PRIMARY KEY (`ID`, `CHART_DIV_ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


-- -For Dashboard menu -----
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 1;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
        VALUES (NULL, 'dashboard.nhtsDashboard','Dashboard ', 'agrodashboard_list.action', @menu_order, @parent_id, '0', '0','0');

SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'dashboard.nhtsDashboard.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='dashboard.nhtsDashboard.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='dashboard.nhtsDashboard.list'));

 -- Dashboard Scripts -- 
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`) VALUES (NULL, 'chart_plot_06', 'donut', 'Buyer - Wise Shipments ', NULL, 'Customers', 'Count', NULL, 'Shipments', 'true', 'true', NULL, 'Shipment Count', NULL, NULL, NULL, 'select c.CUSTOMER_NAME ,count(s.id) as a1,count(s.id) as a2 from shipment s join customer c on s.CUSTOMER_ID=c.id join warehouse p on s.PACKHOUSE_ID = p.id group by s.CUSTOMER_ID', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 4, 0, 's.created_date', 'p.exporter', NULL, NULL, 0);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`) VALUES (NULL, 'chart_plot_03', 'pie', 'Farmers', NULL, 'Farmers', 'Count', NULL, 'Farmers', 'true', 'true', NULL, 'Farmers', NULL, '<b>{point.name}</b>: {point.y}', '{series.name}: <b>{point.y}</b>', 'select count(*),sum(case when  status=\'1\' and status_code=0 then 1 else 0 end) ,sum(case when status=\'0\' or status_code!=0 then 1 else 0 end ) from farmer', 0, 'nhts', NULL, NULL, NULL, NULL, NULL, 1, 1, 'created_date', 'exporter_id', NULL, NULL, 0);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`) VALUES (NULL, 'chart_plot_04', 'pie', 'Farms', NULL, 'Farms', 'Count', NULL, 'Farms', 'true', 'true', NULL, 'Farms', NULL, '<b>{point.name}</b>: {point.y}', '{series.name}: <b>{point.y}</b>', 'select count(*),sum(case when  status=\'1\'  then 1 else 0 end) ,sum(case when status!=\'1\' then 1 else 0 end ) from farm', 0, 'nhts', NULL, NULL, NULL, NULL, NULL, 2, 1, 'created_date', 'exporter_id', NULL, NULL, 0);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`) VALUES (NULL, 'chart_plot_05', 'stackedBar', 'Estimated Vs Actual Yield', NULL, 'Crops', 'Yield', 'Crops', 'Kg', 'true', 'true', NULL, 'Crops', NULL, NULL, '{series.name}: <b>{point.y}</b>', 'SELECT pv.NAME,pv.CODE,sum(IFNULL(fc.EST_YIELD,0)) AS est, sum(IFNULL(ha.QUANTITY_HARVESTED,0)) AS actual FROM farm_crops fc INNER JOIN harvest ha ON ha.FARM_CROPS_ID= fc.id and fc.status=1 INNER JOIN farm fa on fa.id=fc.FARM_ID AND fa.STATUS = 1 INNER JOIN farmer f ON f.ID = fa.FARMER_ID 	AND f.STATUS_CODE = 0 AND f.STATUS =1 INNER JOIN procurement_variety pv ON pv.id = fc.variety  ', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 3, 1, 'fc.planting_date', 'f.exporter_id', 'fc.variety', 'pv.NAME ', 0);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`) VALUES (NULL, 'chart_plot_07', 'stackedBar', 'Harvested Vs Sorted ', NULL, 'Crops', 'Quantity', 'Crops', 'Kg', 'true', 'true', NULL, 'Crops', NULL, NULL, '{series.name}: <b>{point.y}</b>', 'SELECT pv.NAME,pv.CODE,sum( IFNULL( ha.QUANTITY_HARVESTED, 0 ) ) AS harvested,sum( IFNULL( s.HARVEST_QTY, 0 ) ) AS sorted FROM harvest ha inner join farm_crops fc ON ha.FARM_CROPS_ID = fc.id AND fc.STATUS = 1 inner join sorting s on  s.FARM_CROPS_ID = fc.id INNER JOIN farm fa ON fa.id = fc.FARM_ID AND fa.STATUS = 1 INNER JOIN farmer f ON f.ID = fa.FARMER_ID AND f.STATUS_CODE = 0 AND f.STATUS = 1 INNER JOIN procurement_variety pv ON pv.id = fc.variety \n', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 5, 1, 'fc.planting_date', 'f.exporter_id', 'fc.variety', 'pv.NAME ', 0);

ALTER TABLE `dashboard` ADD COLUMN `PAGINATION` varchar(10) NULL;
ALTER TABLE `dashboard` ADD COLUMN `BAR_LABELS` varchar(150) NULL;

ALTER TABLE `eses_nhts`.`farmer` 
ADD COLUMN `COMPANY_NAME` varchar(255) NULL AFTER `FARMER_CAT`;
ALTER TABLE `eses_nhts`.`farmer` 
ADD COLUMN `KRA_PIN` varchar(255) NULL AFTER `COMPANY_NAME`;
ALTER TABLE `eses_nhts`.`farmer` 
ADD COLUMN `REGISTRATION_CERTIFICATE` varchar(255) NULL AFTER `KRA_PIN`;
ALTER TABLE `eses_nhts`.`farmer` 
ADD COLUMN `FARMER_REG_TYPE` varchar(255) NULL AFTER `REGISTRATION_CERTIFICATE`;

INSERT INTO `eses_nhts`.`locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'reset.resetListDatePacking', 'en', 'Product details already exist.Are you sure to change the Packing Date?');
INSERT INTO `eses_nhts`.`locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'reset.resetListDataShipment', 'en', 'Product details already exist.Are you sure to change the Shipment Date?');
INSERT INTO `eses_nhts`.`locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'reset.resetListDataRecalling', 'en', 'Product details already exist.Are you sure to change the Recalling Date?');
INSERT INTO `eses_nhts`.`locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'reset.resetListDataPostRecalling', 'en', 'Product details already exist.Are you sure to change the Inspection Date?');

ALTER TABLE `eses_nhts`.`document_upload` 
ADD COLUMN `CREATED_USER` varchar(255) NULL AFTER `FARM_ID`;
ALTER TABLE `eses_nhts`.`document_upload` 
ADD COLUMN `CREATED_DATE` date NULL AFTER `CREATED_USER`;
ALTER TABLE `eses_nhts`.`document_upload` 
ADD COLUMN `UPDATED_USER` varchar(255) NULL AFTER `CREATED_DATE`;
ALTER TABLE `eses_nhts`.`document_upload` 
ADD COLUMN `UPDATED_DATE` date NULL AFTER `UPDATED_USER`;

---------------------Dashboard DB old Script in QA---------------
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (6, 'chart_plot_06', 'donut', 'Buyer - Wise Shipments ', NULL, 'Customers', 'Count', NULL, 'Kg', 'true', 'true', NULL, 'Shipment Count', NULL, NULL, NULL, 'select c.CUSTOMER_NAME ,count(s.id) as a1,count(s.id) as a2 from shipment s join customer c on s.CUSTOMER_ID=c.id  and s.status=1 join warehouse p on s.PACKHOUSE_ID = p.id ', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 4, 1, 's.shipment_date', 'p.exporter', 's.CUSTOMER_ID', NULL, 0, NULL, NULL);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (13, 'chart_plot_03', 'pie', 'Farmers', NULL, 'Farmers', 'Count', NULL, 'Farmers', 'true', 'true', NULL, 'Farmers', NULL, '<b>{point.name}</b>: {point.y}', '{series.name}: <b>{point.y}</b>', 'select count(*),sum(case when  status=\'1\' and status_code=0 then 1 else 0 end) ,sum(case when status=\'0\' or status_code!=0 then 1 else 0 end ) from farmer', 0, 'nhts', NULL, NULL, NULL, NULL, NULL, 1, 1, 'created_date', 'exporter_id', NULL, NULL, 0, NULL, NULL);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (19, 'chart_plot_04', 'pie', 'Farms', NULL, 'Farms', 'Count', NULL, 'Farms', 'true', 'true', NULL, 'Farms', NULL, '<b>{point.name}</b>: {point.y}', '{series.name}: <b>{point.y}</b>', 'select count(*),sum(case when  status=\'1\'  then 1 else 0 end) ,sum(case when status!=\'1\' then 1 else 0 end ) from farm', 0, 'nhts', NULL, NULL, NULL, NULL, NULL, 2, 1, 'created_date', 'exporter_id', NULL, NULL, 0, NULL, NULL);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (20, 'chart_plot_05', 'comparisonBar', 'Estimated Vs Actual Yield', NULL, 'Crops', 'Yield', 'Crops', 'Kg', 'true', 'true', NULL, 'Crops', NULL, NULL, '{series.name}: <b>{point.y}</b>', 'SELECT pv.NAME,pv.CODE,ROUND(SUM(IFNULL(fc.EXP_HARVEST_QTY,0)),2), sum(IFNULL(ha.QUANTITY_HARVESTED,0)) AS actual FROM farm_crops fc INNER JOIN harvest ha ON ha.FARM_CROPS_ID= fc.id and fc.status=1 and ha.status=1 INNER JOIN farm fa on fa.id=fc.FARM_ID AND fa.STATUS = 1 INNER JOIN farmer f ON f.ID = fa.FARMER_ID join procurement_variety pv on pv.id =fc.VARIETY', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 3, 1, 'ha.date_harvested', 'f.exporter_id', 'fc.variety', 'pv.NAME ', 0, NULL, 'Estimated Yield , Actual Yield');
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (21, 'chart_plot_07', 'comparisonBar', 'Harvested Vs Sorted ', NULL, 'Crops', 'Quantity', 'Crops', 'Kg', 'true', 'true', NULL, 'Crops', NULL, NULL, '{series.name}: <b>{point.y}</b>', 'SELECT pv.NAME,pv.CODE,sum( IFNULL( ha.QUANTITY_HARVESTED, 0 ) ) AS harvested,sum( IFNULL( s.NET_QTY, 0 ) ) AS sorted FROM harvest ha inner join farm_crops fc ON ha.FARM_CROPS_ID = fc.id AND fc.STATUS = 1  and ha.status=1 inner join sorting s on  s.FARM_CROPS_ID = fc.id INNER JOIN farm fa ON fa.id = fc.FARM_ID AND fa.STATUS = 1 INNER JOIN farmer f ON f.ID = fa.FARMER_ID AND f.STATUS_CODE = 0 AND f.STATUS = 1  and s.status=1 INNER JOIN procurement_variety pv ON pv.id = fc.variety ', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 5, 1, 's.created_date', 'f.exporter_id', 'fc.variety', 'pv.NAME ', 0, '1', 'Harvested , Sorted');

---------------------Dashboard DB new Script in QA---------------
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (6, 'chart_plot_06', 'donut', 'Buyer - Wise Shipments ', NULL, 'Customers', 'Count', NULL, 'Kg', 'true', 'true', NULL, 'Shipment Count', NULL, NULL, NULL, 'select c.CUSTOMER_NAME ,count(s.id) as a1,count(s.id) as a2 from shipment s join customer c on s.CUSTOMER_ID=c.id  and s.status=1 join warehouse p on s.PACKHOUSE_ID = p.id ', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 4, 1, 's.shipment_date', 'p.exporter', 's.CUSTOMER_ID', NULL, 0, NULL, NULL);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (13, 'chart_plot_03', 'pie', 'Farmers', NULL, 'Farmers', 'Count', NULL, 'Farmers', 'true', 'true', NULL, 'Farmers', NULL, '<b>{point.name}</b>: {point.y}', '{series.name}: <b>{point.y}</b>', 'select count(*),sum(case when  status=\'1\' and status_code=0 then 1 else 0 end) ,sum(case when status=\'0\' or status_code!=0 then 1 else 0 end ) from farmer', 0, 'nhts', NULL, NULL, NULL, NULL, NULL, 1, 1, 'created_date', 'exporter_id', NULL, NULL, 0, NULL, NULL);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (19, 'chart_plot_04', 'pie', 'Farms', NULL, 'Farms', 'Count', NULL, 'Farms', 'true', 'true', NULL, 'Farms', NULL, '<b>{point.name}</b>: {point.y}', '{series.name}: <b>{point.y}</b>', 'select count(*),sum(case when  status=\'1\'  then 1 else 0 end) ,sum(case when status!=\'1\' then 1 else 0 end ) from farm', 0, 'nhts', NULL, NULL, NULL, NULL, NULL, 2, 1, 'created_date', 'exporter_id', NULL, NULL, 0, NULL, NULL);
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (20, 'chart_plot_05', 'comparisonBar', 'Estimated Vs Actual Yield', NULL, 'Crops', 'Yield', 'Crops', 'Kg', 'true', 'true', NULL, 'Crops', NULL, NULL, '{series.name}: <b>{point.y}</b>', 'SELECT pv.NAME,pv.CODE,ROUND(SUM(IFNULL(fc.EXP_HARVEST_QTY,0)),2), ROUND(sum(IFNULL(ha.QUANTITY_HARVESTED,0)),2) AS actual FROM farm_crops fc INNER JOIN harvest ha ON ha.FARM_CROPS_ID= fc.id and fc.status=1 and ha.status=1 INNER JOIN farm fa on fa.id=fc.FARM_ID AND fa.STATUS = 1 INNER JOIN farmer f ON f.ID = fa.FARMER_ID join procurement_variety pv on pv.id =fc.VARIETY', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 3, 1, 'ha.date_harvested', 'f.exporter_id', 'fc.variety', 'pv.NAME ', 0, NULL, 'Estimated Yield , Actual Yield');
INSERT INTO `eses_nhts`.`dashboard`(`ID`, `CHART_DIV_ID`, `CHART_TYPE`, `CHART_TITLE`, `CHART_SUB_TITLE`, `X_AXIS_CATEGORY`, `Y_AXIS_TITLE`, `X_AXIS_TITLE`, `TOOLTIP_SUFFIX`, `LEGEND_ENABLE`, `STACK_LEGEND_ENABLE`, `SERIES_DATA`, `SERIES_NAME`, `COLOR`, `DATALABEL_FORMAT`, `TOOLTIP_POINT_FORMAT`, `CHART_QUERY`, `STATUS`, `branch_id`, `created_date`, `created_user`, `updated_date`, `updated_user`, `version`, `CHART_ORDER`, `DATE_FILTER`, `DATE_FILTER_FIELD`, `EXPORTER_FILTER_FIELD`, `GROUP_BY_FIELD`, `ORDER_BY_FIELD`, `TABS`, `PAGINATION`, `BAR_LABELS`) VALUES (21, 'chart_plot_07', 'comparisonBar', 'Harvested Vs Sorted ', NULL, 'Crops', 'Quantity', 'Crops', 'Kg', 'true', 'true', NULL, 'Crops', NULL, NULL, '{series.name}: <b>{point.y}</b>', 'SELECT pv.NAME,pv.CODE,ROUND(sum( IFNULL( ha.QUANTITY_HARVESTED, 0 ) ),2) AS harvested,ROUND(sum( IFNULL( s.NET_QTY, 0 ) ),2) AS sorted FROM harvest ha inner join farm_crops fc ON ha.FARM_CROPS_ID = fc.id AND fc.STATUS = 1  and ha.status=1 inner join sorting s on  s.FARM_CROPS_ID = fc.id INNER JOIN farm fa ON fa.id = fc.FARM_ID AND fa.STATUS = 1 INNER JOIN farmer f ON f.ID = fa.FARMER_ID AND f.STATUS_CODE = 0 AND f.STATUS = 1  and s.status=1 INNER JOIN procurement_variety pv ON pv.id = fc.variety ', 1, 'nhts', NULL, NULL, NULL, NULL, NULL, 5, 1, 's.created_date', 'f.exporter_id', 'fc.variety', 'pv.NAME ', 0, '1', 'Harvested , Sorted');


-- - Lat, Lon, IPAddress qry

alter table farm CHANGE   LATITUDE  LAT  varchar(25);

alter table farm CHANGE   LONGITUDE  LON  varchar(25);

alter table farm add column IP_ADDR varchar(50);

alter table farmer CHANGE   FLONG  LON  varchar(25);
alter table farmer add column IP_ADDR varchar(50);



----Farmer Registration------
ALTER TABLE `eses_nhts`.`farmer` 
CHANGE COLUMN `FLong` `LON` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL AFTER `Lat`;
ALTER TABLE `eses_nhts`.`farmer` 
CHANGE COLUMN `Lat` `LAT` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL AFTER `Photo_NID`;
ALTER TABLE `eses_nhts`.`farmer` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `FARMER_REG_TYPE`;

---------------Document Upload---------------
ALTER TABLE `eses_nhts`.`document_upload` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `UPDATED_DATE`;
ALTER TABLE `eses_nhts`.`document_upload` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `IP_ADDR`;
ALTER TABLE `eses_nhts`.`document_upload` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;

---------------Site preparetion---------------
ALTER TABLE `eses_nhts`.`site_prepration` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `MSG_NO`;
ALTER TABLE `eses_nhts`.`site_prepration` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `IP_ADDR`;
ALTER TABLE `eses_nhts`.`site_prepration` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;

---------------Farm---------------
ALTER TABLE `eses_nhts`.`farm` 
CHANGE COLUMN `LONGITUDE` `LON` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `LATITUDE`;
ALTER TABLE `eses_nhts`.`farm` 
CHANGE COLUMN `LATITUDE` `LAT` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `NO_OF_FARM_LABOURS`;
ALTER TABLE `eses_nhts`.`farm` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `PLOTTING_STATUS`;

---------------Planting---------------
ALTER TABLE `eses_nhts`.`farm_crops` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `PLOTTING_STATUS`;
ALTER TABLE `eses_nhts`.`farm_crops` 
CHANGE COLUMN `LONGITUDE` `LON` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `LATITUDE`;
ALTER TABLE `eses_nhts`.`farm_crops` 
CHANGE COLUMN `LATITUDE` `LAT` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `MSG_NO`;


---------------land preparation---------------
ALTER TABLE `eses_nhts`.`land_preparation` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `MSG_NO`;
ALTER TABLE `eses_nhts`.`land_preparation` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`land_preparation` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Scouting---------------
ALTER TABLE `eses_nhts`.`scouting` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `SOURCE_OF_WATER`;
ALTER TABLE `eses_nhts`.`scouting` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`scouting` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Spraying menu---------------
ALTER TABLE `eses_nhts`.`spray_field_management` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `PCBP_ID`;
ALTER TABLE `eses_nhts`.`spray_field_management` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`spray_field_management` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------PCBP---------------
ALTER TABLE `eses_nhts`.`pcbp` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `CHEMICAL_NAME`;
ALTER TABLE `eses_nhts`.`pcbp` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`pcbp` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------harvest---------------
ALTER TABLE `eses_nhts`.`harvest` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `MSG_NO`;
ALTER TABLE `eses_nhts`.`harvest` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`harvest` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Sorting---------------
ALTER TABLE `eses_nhts`.`sorting` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `MSG_NO`;
ALTER TABLE `eses_nhts`.`sorting` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`sorting` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Incoming shipment---------------
ALTER TABLE `eses_nhts`.`packhouse_incoming` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`packhouse_incoming` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `MSG_NO`;
ALTER TABLE `eses_nhts`.`packhouse_incoming` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------packing---------------
ALTER TABLE `eses_nhts`.`packing` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `TOTAL_WT`;
ALTER TABLE `eses_nhts`.`packing` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`packing` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;


---------------Shipment---------------
ALTER TABLE `eses_nhts`.`shipment` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `VERSION`;
ALTER TABLE `eses_nhts`.`shipment` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`shipment` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;


---------------Recalling---------------
ALTER TABLE `eses_nhts`.`recalling` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `BATCH_NO`;
ALTER TABLE `eses_nhts`.`recalling` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`recalling` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Post recall inspection---------------
ALTER TABLE `eses_nhts`.`post_recall_inspection` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `RECALL_ID`;
ALTER TABLE `eses_nhts`.`post_recall_inspection` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`post_recall_inspection` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Customer---------------
ALTER TABLE `eses_nhts`.`customer` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `UPDATED_DATE`;

ALTER TABLE `eses_nhts`.`customer` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`customer` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------warehouse---------------
ALTER TABLE `eses_nhts`.`warehouse` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `FARM_CROPS_ID`;
ALTER TABLE `eses_nhts`.`warehouse` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`warehouse` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Mobile user---------------
ALTER TABLE `eses_nhts`.`agent_prof` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `WAREHOUSE`;
ALTER TABLE `eses_nhts`.`agent_prof` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`agent_prof` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;
ALTER TABLE `eses_nhts`.`prof` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `EXPORTER_ID`;
ALTER TABLE `eses_nhts`.`prof` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`prof` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------Device---------------
ALTER TABLE `eses_nhts`.`device` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `UPDATED_DATE`;
ALTER TABLE `eses_nhts`.`device` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`device` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

---------------user---------------
ALTER TABLE `eses_nhts`.`ese_user` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `CITY_ID`;
ALTER TABLE `eses_nhts`.`ese_user` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`ese_user` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `LON`;

update procurement_variety set VERSION=0 where version is null;
update site_prepration set VERSION=0 where version is null;


ALTER TABLE `eses_nhts`.`dashboard` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `BAR_LABELS`;

ALTER TABLE `eses_nhts`.`dashboard` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `IP_ADDR`;

ALTER TABLE `eses_nhts`.`dashboard` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;

ALTER TABLE `eses_nhts`.`procurement_grade` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `version`;

ALTER TABLE `eses_nhts`.`procurement_product` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `version`;

ALTER TABLE `eses_nhts`.`procurement_variety` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `version`;
---------------  procurement_grade---------------
ALTER TABLE `eses_nhts`.`procurement_grade` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `IP_ADDR`;
ALTER TABLE `eses_nhts`.`procurement_product` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `IP_ADDR`;
ALTER TABLE `eses_nhts`.`procurement_variety` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `IP_ADDR`;
---------------  procurement_grade---------------
ALTER TABLE `eses_nhts`.`procurement_grade` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`procurement_product` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
ALTER TABLE `eses_nhts`.`procurement_variety` 
ADD COLUMN `LON` varchar(255) NULL AFTER `LAT`;
--------------- exporter_registration---------------
ALTER TABLE `eses_nhts`.`exporter_registration` 
ADD COLUMN `IP_ADDR` varchar(255) NULL AFTER `OTHER_SCATTERED`;
ALTER TABLE `eses_nhts`.`exporter_registration` 
ADD COLUMN `LON` varchar(255) NULL AFTER `IP_ADDR`;
ALTER TABLE `eses_nhts`.`exporter_registration` 
ADD COLUMN `LAT` varchar(255) NULL AFTER `LON`;

---------------farm_crops---------------
ALTER TABLE `eses_nhts`.`farm_crops` 
ADD COLUMN `CREATED_USER` varchar(255) NULL AFTER `PLOTTING_STATUS`;
ALTER TABLE `eses_nhts`.`farm_crops` 
ADD COLUMN `CREATED_DATE` date NULL AFTER `CREATED_USER`;
ALTER TABLE `eses_nhts`.`farm_crops` 
ADD COLUMN `UPDATED_USER` varchar(255) NULL AFTER `CREATED_DATE`;
ALTER TABLE `eses_nhts`.`farm_crops` 
ADD COLUMN `UPDATED_DATE` date NULL AFTER `UPDATED_USER`;

---------------txn_lot---------------
ALTER TABLE `eses_nhts`.`txn_log` 
ADD COLUMN `CREATED_USER` varchar(255) NULL AFTER `BRANCH_ID`;
ALTER TABLE `eses_nhts`.`txn_log` 
ADD COLUMN `CREATED_DATE` date NULL AFTER `CREATED_USER`;
ALTER TABLE `eses_nhts`.`txn_log` 
ADD COLUMN `UPDATED_USER` varchar(255) NULL AFTER `CREATED_DATE`;
ALTER TABLE `eses_nhts`.`txn_log` 
ADD COLUMN `UPDATED_DATE` date NULL AFTER `UPDATED_USER`;
-- - 04-05-2022

ALTER TABLE `packing` 
ADD COLUMN `MSG_NO` varchar(40) NULL ;


ALTER TABLE `shipment` 
ADD COLUMN `MSG_NO` varchar(40) NULL ;

ALTER TABLE `dynamic_report_config_detail` 
ADD COLUMN `SORT_FIELD` longtext NULL ;


ALTER TABLE `land_preparation` 
ADD COLUMN `FARM_CROPS_BLOCKID` varchar(40) NULL ;

INSERT INTO `eses_nhts`.`pref`(`ESE_ID`, `VAL`, `NAME`) VALUES (1, 'V3.0', 'VESION');

INSERT INTO `eses_nhts`.`locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'exporterRegistrationSuccess.msg', 'en', 'Exporter registered successfully. Your Username is your registered Email id and check your mail for the Password.');

----------------------------------------25-05-2022------------------------------------------------
UPDATE `eses_nhts`.`dynamic_report_config_detail` SET `ACESS_TYPE` = 2, `ALIGNMENT` = NULL, `DATA_TYPE` = '5', `EXPRESSION` = NULL, `FIELD` = 'date', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Expected Day of Harvest (EDH)', `METHOD` = 'getGeneralDateFormat', `ORDERR` = 4, `PARAMTERS` = 'harvest_detail.action?id=', `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 103, `SORT_FIELD` = NULL WHERE `ID` = 1199;

ALTER TABLE `eses_nhts`.`spray_field_management` 
ADD COLUMN `END_DATE_OF_SPRAYING` date NULL AFTER `IP_ADDR`;
ALTER TABLE `eses_nhts`.`sorting` 
ADD COLUMN `DRIVER_CONTACT` varchar(255) NULL AFTER `IP_ADDR`;
ALTER TABLE `eses_nhts`.`sorting` 
ADD COLUMN `DRIVER_NAME` varchar(255) NULL AFTER `DRIVER_CONTACT`;
ALTER TABLE `eses_nhts`.`sorting` 
ADD COLUMN `TRUCK_TYPE` varchar(255) NULL AFTER `DRIVER_NAME`;
ALTER TABLE `eses_nhts`.`sorting` 
ADD COLUMN `TRUCK_NO` varchar(255) NULL AFTER `TRUCK_TYPE`;
ALTER TABLE `eses_nhts`.`harvest` 
ADD COLUMN `HARVEST_TYPE` varchar(255) NULL AFTER `IP_ADDR`;

INSERT INTO `eses_nhts`.`locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'harvestType', 'en', '87');
INSERT INTO `eses_nhts`.`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Type', 1, 1, 87);

ALTER TABLE `eses_nhts`.`spray_field_management` 
ADD COLUMN `OPERATOR_MEDICAL_REPORT` varchar(255) NULL AFTER `END_DATE_OF_SPRAYING`;

---------------------26-05-2022------------------------
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 39, NULL, NULL, NULL, 'id', 0, 1, NULL, 1, NULL, 'Receipt', 'Sorting', 12, NULL, 1, 0, 100, 106, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1449, 39, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Receipt', 'IncomingShipment', 11, NULL, 1, 0, 100, 112, NULL);

ALTER TABLE `eses_nhts`.`agent_access_log_detail` 
ADD COLUMN `UPDATED_USER` varchar(255) NULL AFTER `ACCESS_ID`;
ALTER TABLE `eses_nhts`.`agent_access_log_detail` 
ADD COLUMN `UPDATED_DATE` date NULL AFTER `UPDATED_USER`;
ALTER TABLE `eses_nhts`.`agent_access_log` 
ADD COLUMN `UPDATED_DATE` date NULL AFTER `SERIAL_NO`;
ALTER TABLE `eses_nhts`.`agent_access_log` 
ADD COLUMN `UPDATED_USER` varchar(255) NULL AFTER `UPDATED_DATE`;


------Device menu add entitlement removed-----------
INSERT INTO `eses_nhts`.`ese_menu_action`(`MENU_ID`, `ACTION_ID`) VALUES (7, 2);

-------------print receipt for packing operaations--------
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 39, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Receipt', 'packingOperations', 11, NULL, 1, 0, 100, 16, NULL);

-------------QR code packing operaations --------
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 38, NULL, NULL, NULL, 'batchNo', 0, 0, NULL, 1, NULL, 'QR Code', NULL, 9, NULL, 1, 0, 100, 16, NULL);

-------------QR code incoming shipment--------
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 38, NULL, NULL, NULL, 'batchNo', 0, 0, NULL, 1, NULL, 'QR Code', NULL, 11, NULL, 1, 0, 100, 112, NULL);

-------------QR code Sorting--------
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 38, NULL, NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'QR Code', NULL, 12, NULL, 1, 0, 100, 106, NULL);

----------------Exporter Field Filter query for user list screen ----------------
INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (147, NULL, '1180', 'agroChDealer~1~3', NULL, 'id', 'select acd.id,acd.COMPANY_NAME  from Exporter_registration acd  where acd.IS_ACTIVE=0', 1, 1, 5, 1);

-- -
ALTER TABLE `eses_nhts`.`customer`
ADD COLUMN `EXPORTER_ID` bigint(45) NULL AFTER `IP_ADDR`;

---------------------------Mobile User  status field filter issue is fixed-------------
INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (145, '0|1|4', NULL, 'status~1~5', NULL, 'active', NULL, 1, 0, 1, 90);

---------------21-06-2022---------------
ALTER TABLE `eses_nhts`.`packing_detail` 
ADD COLUMN `REJECT_WT` double(45, 2) NULL AFTER `PACKING_ID`;

---------------12-07-2022---------------
ALTER TABLE `eses_nhts`.`exporter_registration` 
ADD COLUMN `STATUS_MSG` longtext;

---------------------20-09-2022------------
ALTER TABLE `planting` 
ADD COLUMN `FARM_CROP` bigint(45) NULL AFTER `IP_ADDR`;

ALTER TABLE `planting` 
ADD COLUMN `PLANTING_MATERIALS` varchar(255) NULL AFTER `FARM_CROP`;

UPDATE `locale_property` SET `code` = 'title.farmCrops', `lang_code` = 'en', `lang_value` = 'Blocking' WHERE `id` = 964;

ALTER TABLE `scouting` 
ADD COLUMN `PLANTING_ID` bigint(45) NULL AFTER `IP_ADDR`;

ALTER TABLE `spray_field_management` 
ADD COLUMN `PLANTING_ID` bigint(45) NULL AFTER `OPERATOR_MEDICAL_REPORT`;

ALTER TABLE `harvest` 
ADD COLUMN `PLANTING_ID` varchar(45);

ALTER TABLE `sorting` 
ADD COLUMN `PLANTING_ID` varchar(45);

ALTER TABLE `city_warehouse` 
ADD COLUMN `PLANTING_ID` varchar(45);

ALTER TABLE `packing_detail` 
ADD COLUMN `PLANTING_ID` varchar(45);

ALTER TABLE `packhouse_incoming_details` 
ADD COLUMN `PLANTING_ID` varchar(45);

ALTER TABLE `site_prepration` 
ADD COLUMN `FARM_CROPS_ID` varchar(45);

-----------22-09-2022---------------------------
ALTER TABLE `shipment_details` 
ADD COLUMN `PLANTING_ID` bigint(45) NULL AFTER `RECALLING_STATUS`;

ALTER TABLE `recall_details` 
ADD COLUMN `PLANTING_ID` bigint(45) NULL AFTER `UPDATED_DATE`;



---------PS-I-------------
UPDATE `dynamic_report_config` SET `ALIAS` = 'fc=farmCrops,fa=fc.farm,far=fa.farmer,vi=fa.village,ci=vi.city,lo=ci.locality,st=lo.state,va=variety,ex=fc.exporter', `BRANCH_ID` = NULL, `DETAIL_METHOD` = '', `ENTITY_NAME` = 'com.sourcetrace.eses.entity.Planting', `FETCH_TYPE` = 2, `GRID_TYPE` = '1', `GROUP_PROPERTY` = NULL, `PARENT_ID` = NULL, `REPORT` = 'PS I', `STATUS` = '1', `XLS_FILE` = 'PSI', `subGrid` = NULL, `SORTBY` = NULL, `CSV_FILE` = 'PSI' WHERE `ID` = 122;

---------PS-II-------------
UPDATE `dynamic_report_config` SET `ALIAS` = 'fc=farmCrops,fa=fc.farm,far=fa.farmer,vi=fa.village,ci=vi.city,lo=ci.locality,st=lo.state,va=variety,ex=fc.exporter', `BRANCH_ID` = NULL, `DETAIL_METHOD` = '', `ENTITY_NAME` = 'com.sourcetrace.eses.entity.Planting', `FETCH_TYPE` = 2, `GRID_TYPE` = '1', `GROUP_PROPERTY` = NULL, `PARENT_ID` = NULL, `REPORT` = 'PS II', `STATUS` = '1', `XLS_FILE` = 'PSII', `subGrid` = NULL, `SORTBY` = NULL, `CSV_FILE` = 'PSII' WHERE `ID` = 123;


-----23-09-2022--------Failed Farmer Report----------------------


SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'report.failedFarmerReport','Failed Farmer Report', 'dynamicViewReportDT_list.action?id=128', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.failedFarmerReport.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.failedFarmerReport.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.failedFarmerReport.list'));


INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (128, NULL, NULL, NULL, 'com.sourcetrace.eses.entity.Farmer', 2, '1', NULL, NULL, 'Failed Farmer Report', '1', NULL, NULL, NULL, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, NULL, NULL, 'exporters', 0, 1, NULL, 1, NULL, 'Exporter', 'getExporterNames', 3, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, '', '5', NULL, 'firstName', 0, 1, NULL, 1, NULL, 'Farmer Name', NULL, 4, 'farmer_detail.action?id=', 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'farmerId', 0, 1, NULL, 1, NULL, 'Farmer Trace Code', NULL, 5, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'age', 0, 1, NULL, 1, NULL, 'Age', NULL, 6, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'nid', 0, 1, NULL, 1, NULL, 'National ID', NULL, 7, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'mobileNo', 0, 1, NULL, 1, NULL, 'Phone number', '', 8, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'emailId', 0, 1, NULL, 1, NULL, 'Email', '', 9, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 8, NULL, NULL, NULL, 'farmerCat', 0, 1, NULL, 1, NULL, 'Farmer Category', '{\"0\":\"Own\",\"1\":\"Contracted\"}', 10, NULL, 1, 0, 100, 128, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '0', NULL, 'status~1~1', NULL, 'status', NULL, 2, 0, 1, 128);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1614', 'farmerCat~1~2', NULL, 'Farmer Category', '{\"0\":\"Own\",\"1\":\"Contracted\"}', 3, 1, 8, 128);

--------------Parent Report->Failed Report----------------------
INSERT INTO `ese_menu`(`ID`, `FILTER`, `DES`, `DIMENSION`, `EXPORT_AVILABILITY`, `ICON_CLASS`, `NAME`, `ORD`, `PARENT_ID`, `PRIORITY`, `URL`) VALUES (NULL, 0, 'Failed Report', 0, 0, 'fa-location-arrow', 'Failed Report', 20, 4, 0, 'javascript:void(0)');


-----28-09-2022--------Failed Farmer Report----------------------
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 169;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'report.failedFarmerReport','Failed Farmer Report', 'dynamicViewReportDT_list.action?id=128', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.failedFarmerReport.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.failedFarmerReport.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.failedFarmerReport.list'));

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (128, NULL, NULL, NULL, 'com.sourcetrace.eses.entity.Farmer', 2, '1', NULL, NULL, 'Failed Farmer Report', '1', NULL, NULL, NULL, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, NULL, NULL, 'exporters', 0, 1, NULL, 1, NULL, 'Exporter', 'getExporterNames', 3, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, '', '5', NULL, 'firstName', 0, 1, NULL, 1, NULL, 'Farmer Name', NULL, 4, 'farmer_detail.action?id=', 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'farmerId', 0, 1, NULL, 1, NULL, 'Farmer Trace Code', NULL, 5, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'age', 0, 1, NULL, 1, NULL, 'Age', NULL, 6, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'nid', 0, 1, NULL, 1, NULL, 'National ID', NULL, 7, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'mobileNo', 0, 1, NULL, 1, NULL, 'Phone number', '', 8, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'emailId', 0, 1, NULL, 1, NULL, 'Email', '', 9, NULL, 1, 0, 100, 128, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 8, NULL, NULL, NULL, 'farmerCat', 0, 1, NULL, 1, NULL, 'Farmer Category', '{\"0\":\"Own\",\"1\":\"Contracted\"}', 10, NULL, 1, 0, 100, 128, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '0', NULL, 'status~1~1', NULL, 'status', NULL, 2, 0, 1, 128);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1614', 'farmerCat~1~2', NULL, 'Farmer Category', '{\"0\":\"Own\",\"1\":\"Contracted\"}', 3, 1, 8, 128);

-----28-09-2022--------Packing Operations Report----------------------
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 169;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'report.packingOperations','Packing Operations', 'dynamicViewReportDT_list.action?id=129', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.packingOperations.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.packingOperations.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.packingOperations.list'));

INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (NULL, 'ph=packHouse,ex=ph.exporter', NULL, NULL, 'com.sourcetrace.eses.entity.Packing', 2, '1', NULL, NULL, 'Packing Operations', '1', 'PackingOperations', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 129, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 129, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, '5', NULL, 'packingDate', 0, 1, NULL, 1, NULL, 'Packing Date', 'getGeneralDateFormat', 3, 'packing_detail.action?id=', 1, 0, 100, 129, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 4, NULL, 1, 0, 100, 129, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ph.name', 0, 1, NULL, 1, NULL, 'Pack House', NULL, 5, NULL, 1, 0, 100, 129, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'packerName', 0, 1, NULL, 1, NULL, 'Packer Name', NULL, 6, NULL, 1, 0, 100, 129, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Lot Number', NULL, 7, NULL, 1, 0, 100, 129, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 39, NULL, NULL, NULL, 'batchNo', 0, 0, NULL, 1, NULL, 'Receipt', 'packingOperations', 9, NULL, 1, 0, 100, 129, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (341, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 1, 0, 1, 129);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (342, '4', NULL, 'status~1~1', NULL, 'status', NULL, 2, 0, 1, 129);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (343, NULL, '1621', 'packingDate~11', 1, 'Packing Date', NULL, 9, 1, 4, 129);

-----28-09-2022--------Incoming Shipment Report----------------------
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 169;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'report.incomingShipment','Incoming Shipment', 'dynamicViewReportDT_list.action?id=130', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.incomingShipment.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.incomingShipment.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.incomingShipment.list'));


INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (NULL, 'ph=packhouse,ex=ph.exporter', NULL, NULL, 'com.sourcetrace.eses.entity.PackhouseIncoming', 2, '1', NULL, NULL, 'Incoming Shipment', '1', 'IncomingShipment', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, NULL, NULL, 'offLoadingDate', 0, 1, NULL, 1, NULL, 'Offloading Date', 'getGeneralDateFormat', 3, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 4, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ph.name', 0, 1, NULL, 1, NULL, 'Packhouse', NULL, 5, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'truckType', 0, 1, NULL, 1, NULL, 'Truck Type', NULL, 5, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'truckNo', 0, 1, NULL, 1, NULL, 'Truck No', NULL, 6, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverCont', 0, 1, NULL, 1, NULL, 'Driver Contact', NULL, 7, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverName', 0, 1, NULL, 1, NULL, 'Driver Name', NULL, 8, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Reception Batch No', NULL, 9, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, '13', NULL, 'totalWeight', 0, 1, NULL, 1, NULL, 'Total Weight', NULL, 10, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 39, NULL, NULL, NULL, 'batchNo', 0, 0, NULL, 1, NULL, 'Receipt', 'IncomingShipment', 12, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1650, 28, 'center', NULL, NULL, 'id', 0, 1, NULL, 1, NULL, 'Edit', 'select distinct p.id,case when group_concat(ps.id) is null THEN	\'true\' ELSE 	\'false\' END from packhouse_incoming p left  join packing_detail pp on pp.batch_No=p.BATCH_NO left join packing ps on ps.id =pp.PACKING_ID and ps.status=1 group by p.id', 13, 'packhouseIncoming_update.action?id=##service.packhouseIncoming.update', 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1651, 23, 'center', NULL, NULL, 'id', 0, 1, NULL, 1, NULL, 'Delete', 'select distinct p.id,case when group_concat(ps.id) is null THEN	\'true\' ELSE 	\'false\' END from packhouse_incoming p left  join packing_detail pp on pp.batch_No=p.BATCH_NO left join packing ps on ps.id =pp.PACKING_ID and ps.status=1 group by p.id', 14, 'packhouseIncoming_delete.action?id=##service.packhouseIncoming.delete', 1, 0, 100, 130, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '4', NULL, 'status~1~1', NULL, 'Status', NULL, 1, 0, 1, 130);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 2, 0, 1, 130);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1629', 'offLoadingDate~11', 1, 'Offloading Date', NULL, 9, 1, 4, 130);

-----28-09-2022--------Shipment Report----------------------
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 169;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'report.shipmentForfailed','Shipment', 'dynamicViewReportDT_list.action?id=131', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.shipmentForfailed.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.shipmentForfailed.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.shipmentForfailed.list'));


INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (NULL, 'p=packhouse,ex=p.exporter,c=customer', NULL, NULL, 'com.sourcetrace.eses.entity.Shipment', 2, '1', NULL, NULL, 'Shipment', '1', 'Shipment', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, '5', NULL, 'shipmentDate', 0, 1, NULL, 1, NULL, 'Shipment Date', 'getGeneralDateFormat', 3, 'shipment_detail.action?id=', 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 4, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'p.name', 0, 1, NULL, 1, NULL, 'Packhouse', NULL, 5, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'c.customerName', 0, 1, NULL, 1, NULL, 'Buyer', NULL, 6, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'pConsignmentNo', 0, 1, NULL, 1, NULL, 'UCR Kentrade', NULL, 7, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'kenyaTraceCode', 0, 1, NULL, 1, NULL, 'Trace Code', NULL, 8, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, '13', NULL, 'totalShipmentQty', 0, 1, NULL, 1, NULL, 'Total Shipment Quantity(KG)', NULL, 9, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 38, NULL, NULL, NULL, 'pConsignmentNo', 0, 0, NULL, 1, NULL, 'QR Code', NULL, 10, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 40, NULL, NULL, NULL, 'pConsignmentNo', 0, 0, NULL, 1, NULL, 'Receipt', NULL, 11, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1652, 28, 'center', NULL, NULL, 'id', 0, 1, NULL, 1, NULL, 'Edit', 'select distinct s.id,case when r.id is null THEN	\'true\' ELSE 	\'false\' END from shipment s left join  recalling r  on r.KENYA_TRACE_CODE=s.PRODUCE_CONSIGNMENT_NO and r.`STATUS`=1', 12, 'shipment_update.action?id=##service.shipment.update', 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1653, 23, 'center', NULL, NULL, 'id', 0, 1, NULL, 1, NULL, 'Delete', 'select distinct s.id,case when r.id is null THEN	\'true\' ELSE 	\'false\' END from shipment s left join  recalling r  on r.KENYA_TRACE_CODE=s.PRODUCE_CONSIGNMENT_NO and r.`STATUS`=1', 13, 'shipment_delete.action?id=##service.shipment.delete', 1, 0, 100, 131, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (344, '4', NULL, 'status~1~5', NULL, 'Status', NULL, 2, 0, 1, 131);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (345, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 1, 0, 1, 131);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (346, NULL, '1373', 'shipmentDate~11', 1, 'Shipment Date', NULL, 9, 1, 4, 131);



--------28-09-2022--------------------------------------------


ALTER TABLE `sorting` 
ADD COLUMN `QR_CODE_ID` varchar(60);

ALTER TABLE `packing_detail` 
ADD COLUMN `QR_CODE_ID` varchar(60);

ALTER TABLE `packhouse_incoming_details` 
ADD COLUMN `QR_CODE_ID` varchar(60);

ALTER TABLE `shipment_details` 
ADD COLUMN `QR_CODE_ID` varchar(60);


ALTER TABLE `packhouse_incoming_details` 
ADD COLUMN `STATUS` bigint(8);

ALTER TABLE `shipment_details` 
ADD COLUMN `STATUS` bigint(8);

------------Planting Report--------------
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'report.planting','Planting', 'dynamicViewReportDT_list.action?id=132', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.planting.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.planting.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.planting.list'));

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (132, 'fc=farmCrops,v=variety,g=grade,pp=species,ex=fc.exporter,f=fc.farm,fa=f.farmer', NULL, NULL, 'com.sourcetrace.eses.entity.Planting', 2, '1', NULL, NULL, 'Planting', '1', NULL, NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1654, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1655, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1656, 1, NULL, NULL, NULL, 'f.farmName', 0, 1, NULL, 1, NULL, 'Farm', NULL, 3, NULL, 1, 0, 100, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1664, 1, NULL, NULL, NULL, 'fc.blockId', 0, 1, NULL, 1, NULL, 'Block Id', NULL, 4, NULL, 1, 0, 100, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1665, 2, NULL, '4', NULL, 'plantingDate', 0, 1, NULL, 1, NULL, 'Planting Date', 'getGeneralDateFormat', 5, NULL, 1, 0, 100, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1666, 1, NULL, NULL, NULL, 'plantingId', 0, 1, NULL, 1, NULL, 'Planting Id', NULL, 6, NULL, 1, 0, 100, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1667, 1, NULL, NULL, NULL, 'v.name', 0, 1, NULL, 1, NULL, 'Crop', NULL, 7, NULL, 1, 0, 100, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1668, 1, NULL, NULL, NULL, 'g.name', 0, 1, NULL, 1, NULL, 'Crop Variety', NULL, 8, NULL, 1, 0, 100, 132, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1669, 1, NULL, NULL, NULL, 'cultiArea', 0, 1, NULL, 1, NULL, 'Planting Area(Acre)', NULL, 9, NULL, 1, 0, 100, 132, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'status', NULL, 1, 0, 1, 132);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1665', 'plantingDate~11', 1, 'Plantint Date', NULL, 2, 1, 4, 132);


------------User Management Log Report--------------
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
 VALUES (NULL, 'report.userEditHistory','User Management Log', 'dynamicViewReportDT_list.action?id=133', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.userEditHistory.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.userEditHistory.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.userEditHistory.list'));

INSERT INTO `eses_nhts`.`dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (NULL, NULL, NULL, NULL, 'com.sourcetrace.eses.entity.UserActiveAndInActiveHostory', 2, '1', NULL, NULL, 'User Management Log', '1', NULL, NULL, NULL, NULL);

INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 0, NULL, 0, NULL, 'id', NULL, 1, NULL, 1, 0, 0, 133, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'branchId', NULL, 2, NULL, 1, 0, 100, 133, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, '4', NULL, 'date', 0, 1, NULL, 1, NULL, 'Date', 'getGeneralDateFormat', 3, NULL, 1, 0, 100, 133, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'time', 0, 1, NULL, 1, NULL, 'Time', NULL, 4, NULL, 1, 0, 100, 133, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'userName', 0, 1, NULL, 1, NULL, 'User Name', NULL, 6, NULL, 1, 0, 100, 133, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'loggedUser', 0, 1, NULL, 1, NULL, 'User', NULL, 7, NULL, 1, 0, 100, 133, NULL);
INSERT INTO `eses_nhts`.`dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 8, NULL, NULL, NULL, 'activity', 0, 1, NULL, 1, NULL, 'Status', '{\"0\":\"User Disabled\",\"1\":\"User Enabled\",\"4\":\"Locked\",\"5\":\"User Created\"}', 8, NULL, 1, 0, 100, 133, NULL);

INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1672', 'date~11', 1, 'Date', NULL, 1, 1, 4, 133);
INSERT INTO `eses_nhts`.`dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1677', 'activity~1~1', NULL, 'activity', '{\"0\":\"User Disabled\",\"1\":\"User Enabled\",\"4\":\"Locked\",\"5\":\"User Created\"}', 2, 1, 8, 133);



-- ------------------------------ Table structure for user_active_and_inactive_hostory-- ----------------------------
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `user_active_and_inactive_hostory`;
CREATE TABLE `user_active_and_inactive_hostory`  (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `branch_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `DATE` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `TIME` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `USER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `LOGGED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `ACTIVITY` int(1) NULL DEFAULT NULL,
  `TYPE` int(1) NULL DEFAULT NULL,
  `CREATED_DATE` datetime NULL DEFAULT NULL,
  `CREATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `UPDATED_DATE` datetime NULL DEFAULT NULL,
  `VERSION` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '1',
  `UPDATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `LAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `LON` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `IP_ADDR` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;


ALTER TABLE `city_warehouse` 
ADD COLUMN `QR_CODE_ID` varchar(60);

UPDATE `locale_property` SET `code` = 'statuss0', `lang_code` = 'en', `lang_value` = 'Inactive' WHERE `id` = 783;



ALTER TABLE `city_warehouse` 
ADD COLUMN `PACKING_BATCH` varchar(60);

---------------27-01-2023---------------
ALTER TABLE `farmer` 
ADD COLUMN `CROP_VARIETY` varchar(255) NULL AFTER `EXPORTERS`;
ALTER TABLE `user_active_and_inactive_hostory` 
ADD COLUMN `EXPORTER` bigint(54) NULL AFTER `IP_ADDR`;
ALTER TABLE `procurement_grade` 
ADD COLUMN `Crop_HS_code` varchar(255) NULL AFTER `LON`;


---For Product Transfer Server menu -----
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
        VALUES (NULL, 'service.productTransfer','Product Transfer', 'dynamicViewReportDT_list.action?id=134', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productTransfer.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productTransfer.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productTransfer.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productTransfer.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productTransfer.delete'));

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (134, 'tf=transferFrom,tt=transferTo,ex=exporter', NULL, 'productTransfer_create.action$$service.productTransfer.create', 'com.sourcetrace.eses.entity.ProductTransfer', 2, '1', NULL, NULL, 'Product Transfer', '1', 'Product Transfer', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Transfer Receipt ID', NULL, 3, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, '5', NULL, 'date', 0, 1, NULL, 1, NULL, 'Date', 'getGeneralDateFormat', 4, 'productTransfer_detail.action?id=', 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 5, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tf.name', 0, 1, NULL, 1, NULL, 'Transfer From', NULL, 6, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tt.name', 0, 1, NULL, 1, NULL, 'Transfer To', NULL, 7, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'truckNo', 0, 1, NULL, 1, NULL, 'Truck No', NULL, 8, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverName', 0, 1, NULL, 1, NULL, 'Driver Name', NULL, 9, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverLicenseNumber', 0, 1, NULL, 1, NULL, 'Driver License Number', NULL, 10, NULL, 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 28, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Edit', 'select distinct pt.id, case when pr.id is null THEN	\'true\' ELSE 	\'false\' END  from product_transfer pt left join product_transfer pr on pr.TRANSFER_RECEIPT_ID=pt.BATCH_NO and pr.status=1', 20, 'productTransfer_update.action?id=##service.productTransfer.update', 1, 0, 100, 134, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 23, 'center', NULL, NULL, 'id', 0, 0, NULL, 1, NULL, 'Delete', 'select distinct pt.id, case when pr.id is null THEN	\'true\' ELSE 	\'false\' END  from product_transfer pt left join product_transfer pr on pr.TRANSFER_RECEIPT_ID=pt.BATCH_NO and pr.status=1', 21, 'productTransfer_delete.action?id=##service.productTransfer.delete', 1, 0, 100, 134, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'Status', NULL, 1, 0, 1, 134);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '0', NULL, 'type~1~1', NULL, 'Type', NULL, 2, 0, 1, 134);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 3, 0, 1, 134);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1711', 'date~11', 1, 'Date', NULL, 9, 1, 4, 134);


---For Product Reception Server menu -----
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 3;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
        VALUES (NULL, 'service.productReception','Product Reception', 'dynamicViewReportDT_list.action?id=135', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '2');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '3');
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '4');

INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productReception.list');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productReception.create');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productReception.update');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'service.productReception.delete');

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.delete'));

INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.create'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.update'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='service.productReception.delete'));

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (135, 'tf=transferFrom,tt=transferTo,ex=tt.exporter', NULL, 'productReception_create.action$$service.productReception.create', 'com.sourcetrace.eses.entity.ProductTransfer', 2, '1', NULL, NULL, 'Product Reception', '1', 'Product Reception', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'transferReceiptID', 0, 1, NULL, 1, NULL, 'Transfer Receipt ID', NULL, 3, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Batch No', NULL, 4, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, '5', NULL, 'date', 0, 1, NULL, 1, NULL, 'Date', 'getGeneralDateFormat', 5, 'productReception_detail.action?id=', 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 6, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tf.name', 0, 1, NULL, 1, NULL, 'Transfer From', NULL, 7, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tt.name', 0, 1, NULL, 1, NULL, 'Transfer To', NULL, 8, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'truckNo', 0, 1, NULL, 1, NULL, 'Truck No', NULL, 9, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverName', 0, 1, NULL, 1, NULL, 'Driver Name', NULL, 10, NULL, 1, 0, 100, 135, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverLicenseNumber', 0, 1, NULL, 1, NULL, 'Driver License Number', NULL, 11, NULL, 1, 0, 100, 135, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'Status', NULL, 1, 0, 1, 135);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'type~1~1', NULL, 'Type', NULL, 2, 0, 1, 135);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 3, 0, 1, 135);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1724', 'date~11', 1, 'Date', NULL, 9, 1, 4, 135);

-- ------------------------------ Table structure for product_transfer-- ----------------------------
DROP TABLE IF EXISTS `product_transfer`;
CREATE TABLE `product_transfer`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BATCH_NO` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TRANSFER_RECEIPT_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DATE` date NULL DEFAULT NULL,
  `TRANSFER_FROM` bigint(45) NULL DEFAULT NULL,
  `TRANSFER_TO` bigint(45) NULL DEFAULT NULL,
  `TRUCK_NO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DRIVER_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `DRIVER_LICENSE_NUMBER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `EXPORTER` bigint(45) NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `STATUS_CODE` int(11) NULL DEFAULT 0,
  `VERSION` int(11) NULL DEFAULT 1,
  `CREATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATED_DATE` date NULL DEFAULT NULL,
  `UPDATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATED_DATE` date NULL DEFAULT NULL,
  `IP_ADDR` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LAT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `LON` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `TYPE` int(1) NULL DEFAULT NULL,
  `MSG_NO` varchar(55) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ------------------------------ Table structure for product_transfer_detail-- ----------------------------
DROP TABLE IF EXISTS `product_transfer_detail`;
CREATE TABLE `product_transfer_detail`  (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `BRANCH_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BATCH_NO` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `BlOCK_ID` bigint(45) NULL DEFAULT NULL,
  `PLANTING_ID` bigint(45) NULL DEFAULT NULL,
  `PRODUCT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `VARIETY` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `AVAILABLE_WEIGHT` double(45, 5) NULL DEFAULT NULL,
  `TRANSFERRED_WEIGHT` double(45, 5) NULL DEFAULT NULL,
  `RECEIVED_WEIGHT` double(45, 5) NULL DEFAULT NULL,
  `PRODUCT_TRANSFER_ID` bigint(45) NULL DEFAULT NULL,
  `STATUS` int(11) NULL DEFAULT NULL,
  `STATUS_CODE` int(11) NULL DEFAULT 0,
  `VERSION` int(11) NULL DEFAULT 1,
  `CREATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `CREATED_DATE` date NULL DEFAULT NULL,
  `UPDATED_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `UPDATED_DATE` date NULL DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `ese_seq`(`ID`, `SEQ_KEY`, `SEQ_VAL`) VALUES (NULL, 'PRODUCT_TRANSFER_ID_SEQ', 0);
INSERT INTO `ese_seq`(`ID`, `SEQ_KEY`, `SEQ_VAL`) VALUES (NULL, 'PRODUCT_RECEPTION_ID_SEQ', 0);
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'mssg.edit.service.productTransfer.update', 'en', 'Can\'t Edit');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'mssg.delete.service.productTransfer.delete', 'en', 'Can\'t Delete');

---For Product Transfer Report menu -----
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
        VALUES (NULL, 'report.productTransfer','Product Transfer Report', 'dynamicViewReportDT_list.action?id=136', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);

INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.productTransfer.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.productTransfer.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.productTransfer.list'));

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (136, 'tf=transferFrom,tt=transferTo,ex=exporter', NULL, NULL, 'com.sourcetrace.eses.entity.ProductTransfer', 2, '1', NULL, NULL, 'Product Transfer Report', '1', 'Product Transfer Report', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Transfer Receipt ID', NULL, 3, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, '4', NULL, 'date', 0, 1, NULL, 1, NULL, 'Date', 'getGeneralDateFormat', 4, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 5, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tf.name', 0, 1, NULL, 1, NULL, 'Transfer From', NULL, 6, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tt.name', 0, 1, NULL, 1, NULL, 'Transfer To', NULL, 7, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'truckNo', 0, 1, NULL, 1, NULL, 'Truck No', NULL, 8, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverName', 0, 1, NULL, 1, NULL, 'Driver Name', NULL, 9, NULL, 1, 0, 100, 136, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverLicenseNumber', 0, 1, NULL, 1, NULL, 'Driver License Number', NULL, 10, NULL, 1, 0, 100, 136, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'Status', NULL, 1, 0, 1, 136);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '0', NULL, 'type~1~1', NULL, 'Type', NULL, 2, 0, 1, 136);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 3, 0, 1, 136);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1735', 'date~11', 1, 'Date', NULL, 9, 1, 4, 136);


---For Product Reception Report menu -----
SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
        VALUES (NULL, 'report.productReception','Product Reception Report', 'dynamicViewReportDT_list.action?id=137', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.productReception.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.productReception.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.productReception.list'));

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (137, 'tf=transferFrom,tt=transferTo,ex=tt.exporter', NULL, NULL, 'com.sourcetrace.eses.entity.ProductTransfer', 2, '1', NULL, NULL, 'Product Reception Report', '1', 'Product Reception Report', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'transferReceiptID', 0, 1, NULL, 1, NULL, 'Transfer Receipt ID', NULL, 3, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'batchNo', 0, 1, NULL, 1, NULL, 'Batch No', NULL, 4, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 5, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 2, NULL, '4', NULL, 'date', 0, 1, NULL, 1, NULL, 'Date', 'getGeneralDateFormat', 6, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tf.name', 0, 1, NULL, 1, NULL, 'Transfer From', NULL, 7, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'tt.name', 0, 1, NULL, 1, NULL, 'Transfer To', NULL, 8, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'truckNo', 0, 1, NULL, 1, NULL, 'Truck No', NULL, 9, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverName', 0, 1, NULL, 1, NULL, 'Driver Name', NULL, 10, NULL, 1, 0, 100, 137, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'driverLicenseNumber', 0, 1, NULL, 1, NULL, 'Driver License Number', NULL, 11, NULL, 1, 0, 100, 137, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'Status', NULL, 1, 0, 1, 137);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'type~1~1', NULL, 'Type', NULL, 2, 0, 1, 137);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, 'dealer', NULL, 'ex.id~1~2', NULL, 'status', NULL, 3, 0, 1, 137);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1746', 'date~11', 1, 'Date', NULL, 9, 1, 4, 137);


---------farmer list page changes--------
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 8, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'farmerCat', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Farm Ownership', `METHOD` = '{\"0\":\"Own\",\"1\":\"Contracted\"}', `ORDERR` = 11, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 2, `SORT_FIELD` = NULL WHERE `ID` = 1492;
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1754, 8, NULL, NULL, NULL, 'fCat', 0, 1, NULL, 1, NULL, 'Farmer Category', '{\"0\":\"Organic\",\"1\":\"Transition\",\"2\":\"Conventional\"}', 12, NULL, 1, 0, 100, 2, NULL);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (375, NULL, '1754', 'fCat~1~2', NULL, 'Farmer Category', '{\"0\":\"Organic\",\"1\":\"Transition\",\"2\":\"Conventional\"}', 5, 1, 8, 2);

---------farmer failed Report changes--------
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 8, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'farmerCat', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Farm Ownership', `METHOD` = '{\"0\":\"Own\",\"1\":\"Contracted\"}', `ORDERR` = 11, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 131, `SORT_FIELD` = NULL WHERE `ID` = 1649;
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 8, NULL, NULL, NULL, 'fCat', 0, 1, NULL, 1, NULL, 'Farmer Category', '{\"0\":\"Organic\",\"1\":\"Transition\",\"2\":\"Conventional\"}', 12, NULL, 1, 0, 100, 131, NULL);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1754', 'fCat~1~2', NULL, 'Farmer Category', '{\"0\":\"Organic\",\"1\":\"Transition\",\"2\":\"Conventional\"}', 5, 1, 8, 131);

----------------Farmer Category field added in farmer-------------------------
ALTER TABLE `farmer` 
ADD COLUMN `FARMER_CATEGORY` varchar(255) NULL AFTER `CROP_VARIETY`;

-------------------22-02-2023------backUp for the Exporter------------------------
UPDATE `eses_nhts`.`exporter_registration` SET `BRANCH_ID` = 'nhts', `COMPANY_NAME` = 'VA Exporters', `REG_PROOF` = NULL, `VILLAGE` = 22, `ADDRESS` = NULL, `MOBILE_NO` = '9600418030', `EMAIL_ID` = '', `REASON` = NULL, `DOC` = NULL, `INSP_ID` = NULL, `EXPIRE_DATE` = '2023-06-30', `IS_ACTIVE` = 0, `CREATED_DATE` = '2022-09-01 09:06:52', `APPROVAL_DATE` = NULL, `CREATED_USER` = NULL, `UPDATED_DATE` = '2022-11-16 12:56:43', `UPDATED_USER` = 'sts', `VERSION` = '37', `Status` = 1, `CMPY_ORIENTATION` = '1', `REG_NUMBER` = 'P051353949H', `UGANDA_EXPORT` = '2', `REF_LETTERNO` = 'LA11/1002133', `Farmer_HaveFarms` = '3, 2', `SCATTERED` = '1, 2, 3', `Pack_GpsLoc` = 'CN001,CN002', `PACKHOUSE` = '1', `Exp_TinNumber` = 'Street1', `TIN` = 'vanitha@sourcetrace.com', `REX_No` = '', `farm_ToPackhouse` = '', `Pack_ToExitPoint` = '0', `Reg_No` = NULL, `WAREHOUSE` = NULL, `OTHER_SCATTERED` = NULL, `IP_ADDR` = NULL, `LON` = ', , , , , ', `LAT` = ', , , , , ', `STATUS_MSG` = 'RECORD NOT FOUND' WHERE `ID` = 2;
UPDATE `eses_nhts`.`exporter_registration` SET `BRANCH_ID` = 'nhts', `COMPANY_NAME` = 'Test', `REG_PROOF` = NULL, `VILLAGE` = 22, `ADDRESS` = NULL, `MOBILE_NO` = '960014852222', `EMAIL_ID` = '', `REASON` = NULL, `DOC` = NULL, `INSP_ID` = NULL, `EXPIRE_DATE` = NULL, `IS_ACTIVE` = 0, `CREATED_DATE` = '2022-09-02 12:39:10', `APPROVAL_DATE` = NULL, `CREATED_USER` = 'sts', `UPDATED_DATE` = '2022-11-16 12:56:43', `UPDATED_USER` = 'sts', `VERSION` = '16', `Status` = 2, `CMPY_ORIENTATION` = '1', `REG_NUMBER` = 'P011212', `UGANDA_EXPORT` = '2', `REF_LETTERNO` = '', `Farmer_HaveFarms` = '2', `SCATTERED` = '3', `Pack_GpsLoc` = 'CN001', `PACKHOUSE` = '1', `Exp_TinNumber` = 'Street1', `TIN` = 'test@mail.com', `REX_No` = '', `farm_ToPackhouse` = '', `Pack_ToExitPoint` = '1', `Reg_No` = NULL, `WAREHOUSE` = NULL, `OTHER_SCATTERED` = NULL, `IP_ADDR` = NULL, `LON` = '', `LAT` = '', `STATUS_MSG` = 'RECORD NOT FOUND' WHERE `ID` = 3;
UPDATE `eses_nhts`.`exporter_registration` SET `BRANCH_ID` = 'nhts', `COMPANY_NAME` = 'Test', `REG_PROOF` = NULL, `VILLAGE` = 22, `ADDRESS` = NULL, `MOBILE_NO` = '961111118030', `EMAIL_ID` = '', `REASON` = NULL, `DOC` = NULL, `INSP_ID` = NULL, `EXPIRE_DATE` = '2023-06-30', `IS_ACTIVE` = 0, `CREATED_DATE` = '2022-09-08 10:26:22', `APPROVAL_DATE` = NULL, `CREATED_USER` = 'sts', `UPDATED_DATE` = '2022-11-16 12:56:43', `UPDATED_USER` = 'sts', `VERSION` = '50', `Status` = 1, `CMPY_ORIENTATION` = '1', `REG_NUMBER` = 'P051219453I', `UGANDA_EXPORT` = '2', `REF_LETTERNO` = 'LA11/1002133', `Farmer_HaveFarms` = '3', `SCATTERED` = '2', `Pack_GpsLoc` = 'CN002', `PACKHOUSE` = '1', `Exp_TinNumber` = 'Street1', `TIN` = 'testexp@mail.com', `REX_No` = '', `farm_ToPackhouse` = '', `Pack_ToExitPoint` = '1', `Reg_No` = NULL, `WAREHOUSE` = NULL, `OTHER_SCATTERED` = NULL, `IP_ADDR` = NULL, `LON` = ', , ', `LAT` = ', , ', `STATUS_MSG` = 'RECORD NOT FOUND' WHERE `ID` = 4;
UPDATE `eses_nhts`.`exporter_registration` SET `BRANCH_ID` = 'nhts', `COMPANY_NAME` = 'Excel Ltd', `REG_PROOF` = NULL, `VILLAGE` = 56, `ADDRESS` = NULL, `MOBILE_NO` = '0720893421', `EMAIL_ID` = 'stvnganga@gmail.com', `REASON` = NULL, `DOC` = NULL, `INSP_ID` = NULL, `EXPIRE_DATE` = NULL, `IS_ACTIVE` = 0, `CREATED_DATE` = '2023-02-15 09:08:02', `APPROVAL_DATE` = NULL, `CREATED_USER` = 'sts', `UPDATED_DATE` = '2023-02-15 09:08:02', `UPDATED_USER` = 'sts', `VERSION` = '1', `Status` = 1, `CMPY_ORIENTATION` = '1', `REG_NUMBER` = 'P000000030N', `UGANDA_EXPORT` = '4', `REF_LETTERNO` = '2345657', `Farmer_HaveFarms` = '112, 113', `SCATTERED` = '135, 148', `Pack_GpsLoc` = '0808100000,0804400002', `PACKHOUSE` = '1', `Exp_TinNumber` = 'D8 Temple road', `TIN` = 'stvnganga@gmail.com', `REX_No` = '987654343', `farm_ToPackhouse` = 'Charles Njonjo', `Pack_ToExitPoint` = '1', `Reg_No` = NULL, `WAREHOUSE` = NULL, `OTHER_SCATTERED` = NULL, `IP_ADDR` = NULL, `LON` = ', ', `LAT` = ', ', `STATUS_MSG` = 'Something Went Wrong, Please Try Again' WHERE `ID` = 5;


-------------------25-04-2023--------------------------
ALTER TABLE `exporter_registration` 
ADD COLUMN `EXPORTER_STATUS` int(2) NULL AFTER `STATUS_MSG`;
ALTER TABLE `spray_field_management` 
ADD COLUMN `PHI_AND_SPRAYING_DATE` date NULL AFTER `PLANTING_ID`;


--------------------26-04-2023-------------------------
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 8, NULL, NULL, NULL, 'exporterStatus', 0, 1, NULL, 1, NULL, 'Exporter Status', '{\"0\":\"Applied\",\"1\":\"Verified\",\"2\":\"Approved\"}', 15, NULL, 1, 0, 100, 4, NULL);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1756', 'exporterStatus~1~5', NULL, 'Exporter Status', '{\"0\":\"Applied\",\"1\":\"Verified\",\"2\":\"Approved\"}', 9, 1, 8, 4);

ALTER TABLE `spray_field_management` 
ADD COLUMN `ACTIVE_INGREDIENT` varchar(255) NULL AFTER `PLANTING_ID`;

INSERT INTO`farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (88, 'nhts', 'Spraying Required?', 1, 1, 88);

ALTER TABLE `scouting` 
ADD COLUMN `SPRAYING_REQUIRED` varchar(255) NULL AFTER `PLANTING_ID`;
------27-04-2023-----------
INSERT INTO `locale_property` (`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'scouting.sprayingRequired', 'en', 'Spraying Required?');
INSERT INTO `dynamic_report_config_detail` (`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1757, 2, NULL, NULL, NULL, 'sprayingRequired', 0, 1, NULL, 1, NULL, 'Spraying Required?', 'getCatalgueNameByCode', 14, NULL, 1, 0, 100, 96, NULL);
INSERT INTO `dynamic_report_config_filter` (`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1757', 'sprayingRequired', NULL, 'Spraying Required?', 'select code as b1,name  from catalogue_value cv where cv.TYPEZ=88', 10, 1, 5, 96);
INSERT INTO `dynamic_report_config_detail` (`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1758, 2, NULL, NULL, NULL, 'sprayingRequired', 0, 1, NULL, 1, NULL, 'Spraying Required?', 'getCatalgueNameByCode', 14, NULL, 1, 0, 100, 95, NULL);
INSERT INTO `dynamic_report_config_filter` (`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (379, NULL, '1758', 'sprayingRequired', NULL, 'Spraying Required?', 'select code as b1,name  from catalogue_value cv where cv.TYPEZ=88', 10, 1, 5, 95);


-------10-05-2023------

ALTER TABLE `planting` 
ADD COLUMN `FIELD_TYPE` varchar(255) NULL AFTER `PLANTING_MATERIALS`;

INSERT INTO `farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (89, 'nhts', 'Field Type', 1, 1, 89);

ALTER TABLE `packing_detail` 
ADD COLUMN `TOTAL_PRICE` varchar(255) NULL AFTER `QR_CODE_ID`;

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1760, 1, NULL, NULL, NULL, 'shipmentDestination', 0, 1, NULL, 1, NULL, 'Shipment Destination', NULL, 7, NULL, 1, 0, 100, 116, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (1761, 1, NULL, NULL, NULL, 'shipmentDestination', 0, 1, NULL, 1, NULL, 'Shipment Destination', NULL, 7, NULL, 1, 0, 100, 117, NULL);

ALTER TABLE `eses_nhts`.`scouting` 
ADD COLUMN `SCTRECOMMENDATION` varchar(255) NULL AFTER `SPRAYING_REQUIRED`;

ALTER TABLE `shipment` 
ADD COLUMN `SHIPMENT_DESTINATION` varchar(255) NULL AFTER `MSG_NO`;

ALTER TABLE `recalling` 
ADD COLUMN `SHIPMENT_DESTINATION` varchar(255) NULL AFTER `ATTACHMENT`;

ALTER TABLE `spray_field_management` 
ADD COLUMN `recommen` varchar(255) NULL AFTER `ACTIVE_INGREDIENT`;

INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'planting.fieldType', 'en', 'Field Type');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'packing.totalprice', 'en', 'Product Value');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'shipment.shipmentDestination', 'en', 'Shipment Destination');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'sprayAndFieldManagement.recommen', 'en', 'Recommendation');



-----------for Packhouse Report menu---------

SET FOREIGN_KEY_CHECKS = 1;
set @parent_id = 4;
set @menu_order = (SELECT max(ord) FROM ese_menu WHERE PARENT_ID = @parent_id);
set @menu_order = @menu_order+1;

INSERT INTO `ese_menu` (`ID`, `NAME`, `DES`, `URL`, `ORD`, `PARENT_ID`, `FILTER`, `EXPORT_AVILABILITY`, `PRIORITY`)
        VALUES (NULL, 'report.packhouse','Packhouse Report', 'dynamicViewReportDT_list.action?id=140', @menu_order, @parent_id, '0', '0','0');
 
SET @max_menu_id = (SELECT MAX(id) FROM ese_menu);

INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 1);
INSERT INTO `ese_role_menu`( `MENU_ID`, `ROLE_ID`) VALUES ( @max_menu_id, 2);
INSERT INTO `ese_menu_action` (`MENU_ID`, `ACTION_ID`) VALUES (@max_menu_id, '1');
INSERT INTO `ese_ent` (`ID`, `NAME`) VALUES (NULL, 'report.packhouse.list');
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('1', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.packhouse.list'));
INSERT INTO `ese_role_ent` (`ROLE_ID`, `ENT_ID`) VALUES ('2', (SELECT id FROM ese_ent WHERE ese_ent.`NAME`='report.packhouse.list'));

INSERT INTO `dynamic_report_config`(`ID`, `ALIAS`, `BRANCH_ID`, `DETAIL_METHOD`, `ENTITY_NAME`, `FETCH_TYPE`, `GRID_TYPE`, `GROUP_PROPERTY`, `PARENT_ID`, `REPORT`, `STATUS`, `XLS_FILE`, `subGrid`, `SORTBY`, `CSV_FILE`) VALUES (140, 'ex=exporter', NULL, NULL, 'com.sourcetrace.eses.entity.Packhouse', 2, '1', NULL, NULL, 'Packhouse Report', '1', 'Packhouse Report', NULL, NULL, NULL);

INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'id', 0, 1, NULL, 0, NULL, 'Id', '', 1, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'branchId', 0, 1, NULL, 1, NULL, 'Branch Id', '', 2, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, '', NULL, 'code', 0, 1, NULL, 1, NULL, 'Code', NULL, 3, '', 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'name', 0, 1, NULL, 1, NULL, 'Name', NULL, 4, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'location', 0, 1, NULL, 1, NULL, 'Location', NULL, 5, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'phoneNo', 0, 1, NULL, 1, NULL, 'Phone Number', NULL, 6, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'latitude', 0, 1, NULL, 1, NULL, 'Latitude', NULL, 7, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'longitude', 0, 1, NULL, 1, NULL, 'Longitude', NULL, 8, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'address', 0, 1, NULL, 1, NULL, 'Address', NULL, 9, NULL, 1, 0, 100, 140, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 1, NULL, NULL, NULL, 'ex.name', 0, 1, NULL, 1, NULL, 'Exporter', NULL, 10, NULL, 1, 0, 100, 140, NULL);

INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, '1', NULL, 'status~1~1', NULL, 'status', NULL, 1, 0, 1, 140);

----------16-05-2023----------
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'mssg.delete.profile.agent.delete', 'en', 'Device Mapping Done. Cant Delete');
UPDATE `dynamic_report_config_filter` SET `DEFAULT_FILTER` = '1', `CONFIG_DETAIL_ID` = '1037', `FIELD` = 'status~1~1', `IS_DATE_FILTER` = NULL, `LABEL` = 'status', `METHOD` = '{\"0\":\"In Active\",\"1\":\"Active\",\"4\":\"Locked\"}', `ORDERR` = 7, `STATUS` = 1, `TYPE` = 8, `REPORT_CONFIG` = 90 WHERE `ID` = 308;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 23, `ALIGNMENT` = 'center', `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'id', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 0, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Delete', `METHOD` = 'select distinct p.id,case when d.id is not null THEN	\'false\' ELSE 	\'true\' END from prof p left join  device d  on d.AGENT_ID=p.id and p.`STATUS`=1', `ORDERR` = 11, `PARAMTERS` = 'fieldStaff_delete?type=fieldStaff&id=##profile.agent.delete', `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 90, `SORT_FIELD` = NULL WHERE `ID` = 1039;


UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 7, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'shipmentDestination', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Shipment Destination', `METHOD` = 'select distinct c.code,c.name from country c', `ORDERR` = 10, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 116, `SORT_FIELD` = NULL WHERE `ID` = 1760;

------------18-05-2023------------------
ALTER TABLE `shipment` 
ADD COLUMN `SHIPMENT_SUPPORTING_FILES` varchar(255) NULL AFTER `SHIPMENT_DESTINATION`;

------------19-05-2023------------------
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 76, NULL, NULL, NULL, 'shipmentSupportingFiles', 0, 1, NULL, 1, NULL, 'Upload', NULL, 11, NULL, 1, 0, 100, 116, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 76, NULL, NULL, NULL, 'shipmentSupportingFiles', 0, 1, NULL, 1, NULL, 'Upload', NULL, 13, NULL, 1, 0, 100, 117, NULL);

-------------24-05-2023--------Failed Shipment report changes-----------------
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 7, NULL, NULL, NULL, 'shipmentDestination', 0, 1, NULL, 1, NULL, 'Shipment Destination', 'select distinct c.code,c.name from country c', 10, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_detail`(`ID`, `ACESS_TYPE`, `ALIGNMENT`, `DATA_TYPE`, `EXPRESSION`, `FIELD`, `GROUP_PROP`, `IS_EXPORT_AVAILABILITY`, `IS_FOOTER_SUM`, `IS_GRID_AVAILABILITY`, `IS_GROUP_HEADER`, `LABEL_NAME`, `METHOD`, `ORDERR`, `PARAMTERS`, `STATUSS`, `SUM_PROP`, `WIDTH`, `REPORT_CONFIG_ID`, `SORT_FIELD`) VALUES (NULL, 76, NULL, NULL, NULL, 'shipmentSupportingFiles', 0, 1, NULL, 1, NULL, 'Upload', NULL, 11, NULL, 1, 0, 100, 130, NULL);
INSERT INTO `dynamic_report_config_filter`(`ID`, `DEFAULT_FILTER`, `CONFIG_DETAIL_ID`, `FIELD`, `IS_DATE_FILTER`, `LABEL`, `METHOD`, `ORDERR`, `STATUS`, `TYPE`, `REPORT_CONFIG`) VALUES (NULL, NULL, '1760', 'shipmentDestination', NULL, 'shipmentDestination', 'select distinct c.code,c.name from country c', 3, 1, 5, 130);

UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 76, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'id', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Upload', `METHOD` = NULL, `ORDERR` = 11, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 116, `SORT_FIELD` = NULL WHERE `ID` = 1778;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 76, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'id', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Upload', `METHOD` = NULL, `ORDERR` = 13, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 117, `SORT_FIELD` = NULL WHERE `ID` = 1779;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 76, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'id', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Upload', `METHOD` = NULL, `ORDERR` = 11, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 130, `SORT_FIELD` = NULL WHERE `ID` = 1781;

ALTER TABLE `shipment_aud` 
ADD COLUMN `SHIPMENT_SUPPORTING_FILES` varchar(255) NULL AFTER `PACKHOUSE_ID`;

-----------------------------26-05-2023----------------------------------------
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'YesAndNo1', 'en', 'Yes');
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'YesAndNo0', 'en', 'No');

--------backUP------------
'VA Exporters','Excel Ltd'
UPDATE `exporter_registration` SET `BRANCH_ID` = 'nhts', `COMPANY_NAME` = 'VA Exporters', `REG_PROOF` = NULL, `VILLAGE` = 22, `ADDRESS` = NULL, `MOBILE_NO` = '0724893298', `EMAIL_ID` = '', `REASON` = NULL, `DOC` = NULL, `INSP_ID` = NULL, `EXPIRE_DATE` = '2023-06-30', `IS_ACTIVE` = 1, `CREATED_DATE` = '2022-09-01 09:06:52', `APPROVAL_DATE` = NULL, `CREATED_USER` = NULL, `UPDATED_DATE` = '2023-05-25 04:30:55', `UPDATED_USER` = 'vahcd', `VERSION` = '45', `Status` = 1, `CMPY_ORIENTATION` = '1', `REG_NUMBER` = 'P051353949H', `UGANDA_EXPORT` = '4, 2, 3', `REF_LETTERNO` = 'LA11/1002133', `Farmer_HaveFarms` = '124, 135, 165', `SCATTERED` = '180, 205, 245', `Pack_GpsLoc` = '0807110000,0810100000,0709300000', `PACKHOUSE` = '1', `Exp_TinNumber` = 'Street1', `TIN` = 'vanitha@sourcetrace.com', `REX_No` = '', `farm_ToPackhouse` = '', `Pack_ToExitPoint` = '0', `Reg_No` = NULL, `WAREHOUSE` = NULL, `OTHER_SCATTERED` = NULL, `IP_ADDR` = NULL, `LON` = ', , , , , ', `LAT` = ', , , , , ', `STATUS_MSG` = 'Something Went Wrong, Please Try Again', `EXPORTER_STATUS` = 2 WHERE `ID` = 2;
UPDATE `exporter_registration` SET `BRANCH_ID` = 'nhts', `COMPANY_NAME` = 'Excel Ltd', `REG_PROOF` = NULL, `VILLAGE` = 56, `ADDRESS` = NULL, `MOBILE_NO` = '0720893421', `EMAIL_ID` = 'stvnganga@gmail.com', `REASON` = NULL, `DOC` = NULL, `INSP_ID` = NULL, `EXPIRE_DATE` = NULL, `IS_ACTIVE` = 1, `CREATED_DATE` = '2023-02-15 09:08:02', `APPROVAL_DATE` = NULL, `CREATED_USER` = 'sts', `UPDATED_DATE` = '2023-05-25 04:31:25', `UPDATED_USER` = 'vahcd', `VERSION` = '11', `Status` = 1, `CMPY_ORIENTATION` = '1', `REG_NUMBER` = 'P000000030N', `UGANDA_EXPORT` = '4, 3', `REF_LETTERNO` = '2345657', `Farmer_HaveFarms` = '165', `SCATTERED` = '245', `Pack_GpsLoc` = '0709300000', `PACKHOUSE` = '1', `Exp_TinNumber` = 'D8 Temple road', `TIN` = 'stvnganga@gmail.com', `REX_No` = '987654343', `farm_ToPackhouse` = 'Charles Njonjo', `Pack_ToExitPoint` = '1', `Reg_No` = NULL, `WAREHOUSE` = NULL, `OTHER_SCATTERED` = NULL, `IP_ADDR` = NULL, `LON` = ', ', `LAT` = ', ', `STATUS_MSG` = 'Something Went Wrong, Please Try Again', `EXPORTER_STATUS` = 2 WHERE `ID` = 5;
--------------------------
------------05-06-2023------------
UPDATE `locale_property` SET `code` = 'empty.packing.price', `lang_code` = 'en', `lang_value` = 'Please Enter Price Per Kg' WHERE `id` = 977;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'areaIrrrigated', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Area Irrigated (acres)', `METHOD` = NULL, `ORDERR` = 22, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 96, `SORT_FIELD` = NULL WHERE `ID` = 1149;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'areaIrrrigated', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Area Irrigated (acres)', `METHOD` = NULL, `ORDERR` = 21, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 95, `SORT_FIELD` = NULL WHERE `ID` = 1130;
INSERT INTO `farm_catalogue`(`ID`, `BRANCH_ID`, `NAME`, `REVIOSION_NO`, `STATUS`, `CATALOGUE_TYPEZ`) VALUES (NULL, 'nhts', 'Truck Type', 1, 1, 91);
INSERT INTO `locale_property`(`id`, `code`, `lang_code`, `lang_value`) VALUES (NULL, 'truckType', 'en', '91');

ALTER TABLE `customer` 
ADD COLUMN `WARD` varchar(255) NULL AFTER `EXPORTER_ID`,
ADD COLUMN `SUB_COUNTY` varchar(255) NULL AFTER `WARD`,
ADD COLUMN `COUNTY` varchar(255) NULL AFTER `SUB_COUNTY`,
ADD COLUMN `COUNTRY` varchar(255) NULL AFTER `COUNTY`;
ALTER TABLE `customer_aud` 
ADD COLUMN `WARD` varchar(255) NULL AFTER `EXPORTER_ID`,
ADD COLUMN `SUB_COUNTY` varchar(255) NULL AFTER `WARD`,
ADD COLUMN `COUNTY` varchar(255) NULL AFTER `SUB_COUNTY`,
ADD COLUMN `COUNTRY` varchar(255) NULL AFTER `COUNTY`;

UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'shipmentDestination', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Shipment Destination', `METHOD` = NULL, `ORDERR` = 10, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 116, `SORT_FIELD` = NULL WHERE `ID` = 1760;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'shipmentDestination', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Shipment Destination', `METHOD` = NULL, `ORDERR` = 10, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 130, `SORT_FIELD` = NULL WHERE `ID` = 1786;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'shipmentDestination', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Shipment Destination', `METHOD` = NULL, `ORDERR` = 10, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 117, `SORT_FIELD` = NULL WHERE `ID` = 1761;
---Filter not needed for scouting(Shipment Destination) 
-------06-06-2023------
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'truckNo', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Number Plate', `METHOD` = NULL, `ORDERR` = 8, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 134, `SORT_FIELD` = NULL WHERE `ID` = 1715;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'truckNo', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Number Plate', `METHOD` = NULL, `ORDERR` = 9, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 135, `SORT_FIELD` = NULL WHERE `ID` = 1727;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'truckNo', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Number Plate', `METHOD` = NULL, `ORDERR` = 8, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 136, `SORT_FIELD` = NULL WHERE `ID` = 1739;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'truckNo', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Number Plate', `METHOD` = NULL, `ORDERR` = 9, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 137, `SORT_FIELD` = NULL WHERE `ID` = 1749;


----------07-06-2023-----
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'truckNo', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Number Plate', `METHOD` = NULL, `ORDERR` = 7, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 111, `SORT_FIELD` = NULL WHERE `ID` = 1307;
UPDATE `dynamic_report_config_detail` SET `ACESS_TYPE` = 1, `ALIGNMENT` = NULL, `DATA_TYPE` = NULL, `EXPRESSION` = NULL, `FIELD` = 'truckNo', `GROUP_PROP` = 0, `IS_EXPORT_AVAILABILITY` = 1, `IS_FOOTER_SUM` = NULL, `IS_GRID_AVAILABILITY` = 1, `IS_GROUP_HEADER` = NULL, `LABEL_NAME` = 'Number Plate', `METHOD` = NULL, `ORDERR` = 6, `PARAMTERS` = NULL, `STATUSS` = 1, `SUM_PROP` = 0, `WIDTH` = 100, `REPORT_CONFIG_ID` = 112, `SORT_FIELD` = NULL WHERE `ID` = 1311;
