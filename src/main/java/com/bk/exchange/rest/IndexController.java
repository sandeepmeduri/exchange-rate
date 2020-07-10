package com.bk.exchange.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.currency.exchange.util.Constants;

/**
 * @author Sandeep Meduri
 *
 */
@RestController
public class IndexController implements ErrorController{

    /**
     * @return
     */
    @RequestMapping(value = Constants.PATH)
    public String error() {
        return "Please verify the URL. <br/>"
        		+ " Common Errors: <br/>"
        		+ " 1.Date format should be yyyy-mm-dd ex: 2020-01-09<br/>"
        		+ " 2.Should be valid base and target currency codes.";
    }

    @Override
    public String getErrorPath() {
        return Constants.PATH;
    }
}
