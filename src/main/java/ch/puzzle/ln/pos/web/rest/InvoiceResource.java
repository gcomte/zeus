package ch.puzzle.ln.pos.web.rest;

import ch.puzzle.ln.pos.domain.Invoice;
import ch.puzzle.ln.pos.security.AuthoritiesConstants;
import ch.puzzle.ln.pos.service.InvoiceService;
import ch.puzzle.ln.pos.service.dto.InvoiceDTO;
import ch.puzzle.ln.pos.web.rest.errors.BadRequestAlertException;
import ch.puzzle.ln.pos.web.rest.errors.InternalServerErrorException;
import ch.puzzle.ln.pos.web.rest.util.HeaderUtil;
import ch.puzzle.ln.pos.web.rest.vm.DonationVM;
import ch.puzzle.ln.pos.web.rest.vm.OrderVM;
import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class InvoiceResource {

    private static final Logger LOG = LoggerFactory.getLogger(InvoiceResource.class);
    private static final String ENTITY_NAME = "invoice";

    private final InvoiceService invoiceService;

    public InvoiceResource(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/invoice/new")
    @Timed
    public ResponseEntity<InvoiceDTO> createInvoice(@Valid @RequestBody OrderVM order) {
        LOG.debug("REST request to save Order : {}", order);
        Invoice invoice = invoiceService.validateAndMapOrder(order);
        invoiceService.calculatePrice(invoice);
        invoiceService.generateLndInvoice(invoice);
        InvoiceDTO result = invoiceService.saveGenerated(invoice);

        try {
            return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PostMapping("/invoice/donation")
    @Timed
    public ResponseEntity<InvoiceDTO> createDonation(@Valid @RequestBody DonationVM donation) {
        LOG.debug("REST request to save donation : {}", donation);
        Invoice invoice = invoiceService.validateAndMapDonation(donation);
        invoiceService.generateLndInvoice(invoice);
        InvoiceDTO result = invoiceService.saveGenerated(invoice);

        try {
            return ResponseEntity.created(new URI("/api/invoices/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * PUT  /invoices : Updates an existing invoice.
     *
     * @param invoiceDTO the invoiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated invoiceDTO,
     * or with status 400 (Bad Request) if the invoiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the invoiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/invoices")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<InvoiceDTO> updateInvoice(@RequestBody InvoiceDTO invoiceDTO) throws URISyntaxException {
        LOG.debug("REST request to update Invoice : {}", invoiceDTO);
        if (invoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InvoiceDTO result = invoiceService.save(invoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, invoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /invoices : get all the invoices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of invoices in body
     */
    @GetMapping("/invoices")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public List<InvoiceDTO> getAllInvoices() {
        LOG.debug("REST request to get all Invoices");
        return invoiceService.findAll();
    }

    /**
     * GET  /invoices/:id : get the "id" invoice.
     *
     * @param id the id of the invoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the invoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/invoices/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<InvoiceDTO> getInvoice(@PathVariable Long id) {
        LOG.debug("REST request to get Invoice : {}", id);
        Optional<InvoiceDTO> invoiceDTO = invoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(invoiceDTO);
    }

    /**
     * DELETE  /invoices/:id : delete the "id" invoice.
     *
     * @param id the id of the invoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/invoices/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        LOG.debug("REST request to delete Invoice : {}", id);
        invoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
