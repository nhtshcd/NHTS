package com.sourcetrace.eses.service;

import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sourcetrace.eses.entity.ESESystem;
import com.sourcetrace.eses.entity.FarmCatalogue;
import com.sourcetrace.eses.entity.Farmer;
import com.sourcetrace.eses.entity.Packing;
import com.sourcetrace.eses.entity.Shipment;
import com.sourcetrace.eses.util.DateUtil;
import com.sourcetrace.eses.util.StringUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@RequestMapping("/rapi/V1/")
@EnableSwagger2
public class ApiServices {
	@Autowired
	private IFarmerService farmerService;

	@Autowired
	private IUtilService utilService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/shipment/{ucrCode}", method = RequestMethod.GET)
	@ApiOperation(value = "Return Shipment Details of a Particular UCR kentrade", response = JSONObject.class)
	@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Returns"),
			@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
			@ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Access denied") })
	public ResponseEntity<Map> shipmentDeta(
			@ApiParam(value = "UCR Kentrade", required = true) @PathVariable String ucrCode, HttpServletRequest request)
			throws JSONException {
		Map js = new HashMap();
		ESESystem eseSystem = utilService.findPrefernceById("1");
		String INGEN_UNAME = eseSystem.getPreferences().get("S_USERNAME");
		String INGEN_TOKEN = eseSystem.getPreferences().get("S_PW");
		String credentials = request.getHeader("Authorization");
		try {
			if (!isUserAuthenticated(credentials, INGEN_UNAME, INGEN_TOKEN)) {
				js.put("status", "error");
				js.put("errorCode", "401");
				js.put("msg", "Unauthorised User");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(js);
			}
			if (ucrCode == null || StringUtil.isEmpty(ucrCode)) {
				js.put("status", "error");
				js.put("errorCode", "501");
				js.put("msg", "Invalid or Empty UCRCode");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(js);
			}
			Shipment ss = (Shipment) farmerService.findObjectById("from Shipment where pConsignmentNo=? and status=1",
					new Object[]{ucrCode});
			if (ss == null) {
				js.put("status", "error");
				js.put("errorCode", "502");
				js.put("msg", "Shipment does not exist");
				return ResponseEntity.status(HttpStatus.OK).body(js);
			} else {
				js.put("ucrCode", ss.getPConsignmentNo());
				js.put("buyer", ss.getCustomer().getCustomerName());
				js.put("ph", ss.getPackhouse().getName());
				js.put("date", DateUtil.convertDateToString(ss.getShipmentDate(), DateUtil.DATE_FORMAT_1));
				js.put("totQty", String.valueOf(ss.getTotalShipmentQty()));
				js.put("tracecode", ss.getKenyaTraceCode());
				js.put("exporters", ss.getPackhouse().getExporter().getName());
				js.put("license", ss.getPackhouse().getExporter().getRegNumber());
				List detA = new ArrayList<>();
				ss.getShipmentDetails().stream().forEach(uu -> {
					Packing pp = (Packing) farmerService.findObjectById("from Packing p where p.batchNo=?",
							new Object[]{uu.getCityWarehouse().getBatchNo()});
					Map det = new HashMap();
					FarmCatalogue ct = utilService.findCatalogueByCode(uu.getPackingUnit());
					Farmer fm = uu.getCityWarehouse().getFarmcrops().getFarm().getFarmer();
					if (pp != null) {
						det.put("packer", String.valueOf(pp.getPackerName()));
						det.put("packDate", DateUtil.convertDateToString(pp.getPackingDate(), DateUtil.DATE_FORMAT_1));
						det.put("punit", ct == null ? "" : ct.getName());
					}
					det.put("lotNumber", pp.getBatchNo());
					det.put("farmer", (fm.getFirstName() + " " + (fm.getLastName() == null ? "" : fm.getLastName())).trim());
					det.put("county", fm.getVillage().getCity().getLocality().getState().getName());
					det.put("country", fm.getVillage().getCity().getLocality().getState().getCountry().getName());
					det.put("subcounty", fm.getVillage().getCity().getLocality().getName());
					det.put("cropName", uu.getCityWarehouse().getPlanting().getVariety().getName());
					det.put("cropVariety", uu.getCityWarehouse().getPlanting().getGrade().getName());
					det.put("block", uu.getCityWarehouse().getFarmcrops().getBlockId());
					det.put("blockName", uu.getCityWarehouse().getFarmcrops().getBlockName());
					det.put("farmName", uu.getCityWarehouse().getFarmcrops().getFarm().getFarmName());
					det.put("farmId", uu.getCityWarehouse().getFarmcrops().getFarm().getFarmCode());
					det.put("qty", String.valueOf(uu.getPackingQty()));
					det.put("UOM", "Kgs");
					detA.add(det);

				});
				js.put("shipDets", detA);

			}
		} catch (Exception e) {
			js.put("status", "error");
			js.put("errorCode", "500");
			js.put("msg", "Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(js);
		}

		return ResponseEntity.status(HttpStatus.OK).body(js);

	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/shipmentValidation/{ucrCode}", method = RequestMethod.GET)
	@ApiOperation(value = "Return Shipment Details of a Particular UCR kentrade", response = JSONObject.class)
	@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "Returns"),
			@ApiResponse(code = HttpURLConnection.HTTP_NOT_FOUND, message = "Not found"),
			@ApiResponse(code = HttpURLConnection.HTTP_FORBIDDEN, message = "Access denied") })
	public ResponseEntity<Map> shipmentValidDeta(
			@ApiParam(value = "UCR Kentrade", required = true) @PathVariable String ucrCode, HttpServletRequest request)
			throws JSONException {
		Map js = new HashMap();
		ESESystem eseSystem = utilService.findPrefernceById("1");
		try {
			
			if (ucrCode == null || StringUtil.isEmpty(ucrCode)) {
				js.put("status", "error");
				js.put("errorCode", "501");
				js.put("msg", "Invalid or Empty UCRCode");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(js);
			}
			Shipment ss = (Shipment) farmerService.findObjectById("from Shipment where pConsignmentNo=?",
					new Object[]{ucrCode});
			if (ss != null && !StringUtil.isEmpty(ss) ) {
				js.put("status", "failure");
				js.put("code", "00");
				js.put("msg", "UCR Kentrade already Exist");
				return ResponseEntity.status(HttpStatus.OK).body(js);
			} else {
				js.put("msg", "UCR Kentrade Success");
				js.put("code", "300");
				js.put("status", "success");
				return ResponseEntity.status(HttpStatus.OK).body(js);

			}
		} catch (Exception e) {
			js.put("status", "error");
			js.put("errorCode", "500");
			js.put("msg", "Server Error");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(js);
		}

	}
	
	
	private boolean isUserAuthenticated(String authString, String iNGEN_UNAME, String iNGEN_TOKEN) {

		if (authString == null || StringUtil.isEmpty(authString)) {
			return false;
		}

		String base64Credentials = authString.substring("Basic".length()).trim();
		byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
		String decodedAuth = new String(credDecoded, StandardCharsets.UTF_8);

		if (StringUtil.isEmpty(decodedAuth) || !decodedAuth.contains(":")) {
			return false;
		} else {
			final String[] values = decodedAuth.split(":");
			String uname = values[0].toString().trim();
			String pw = values[1].toString().trim();
			if (!uname.equals(iNGEN_UNAME)) {
				return false;
			} else if (!pw.equals(iNGEN_TOKEN)) {
				return false;
			}
		}
		return true;
	}
}
