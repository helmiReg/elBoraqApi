package sbz.padel.backend.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbz.padel.backend.entities.Cheque;
import sbz.padel.backend.generics.BaseController;
import sbz.padel.backend.services.ChequeService;

@RestController()
@RequestMapping("/cheque")
public class ChequeController extends BaseController<Cheque> {
    private final ChequeService chequeService;

    public ChequeController(ChequeService chequeService) {
        super(chequeService);
        this.chequeService = chequeService;
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.chequeService.deleteById(id);
    }

    @GetMapping("/pending/between")
    public Page<Cheque> findAllPendingByDateBetweenOrderAsc(
            @RequestParam(value = "state", required = false, defaultValue = "false") boolean state,
            @RequestParam(value = "fromDate") String fromDate,
            @RequestParam(value = "toDate") String toDate,
            Pageable pageable) {
        DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        LocalDate newFromDate = LocalDate.parse(fromDate, df);
        LocalDate newToDate = LocalDate.parse(toDate, df);
        return this.chequeService.getByStateBetweenOrderByDateAsc(false, newFromDate, newToDate, pageable);
    }

    @GetMapping("/paid/between")
    public Page<Cheque> findAllPaidByDateBetweenOrderAsc(
            @RequestParam(value = "state", required = false, defaultValue = "true") boolean state,
            @RequestParam(value = "fromDate") String fromDate,
            @RequestParam(value = "toDate") String toDate,
            Pageable pageable) {
        DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        LocalDate newFromDate = LocalDate.parse(fromDate, df);
        LocalDate newToDate = LocalDate.parse(toDate, df);
        return this.chequeService.getByStateBetweenOrderByDateAsc(true, newFromDate, newToDate, pageable);
    }

    @GetMapping("/all/between")
    public Page<Cheque> findAllByDateBetweenOrderAsc(
            @RequestParam(value = "fromDate") String fromDate,
            @RequestParam(value = "toDate") String toDate,
            Pageable pageable) {
        DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
        LocalDate newFromDate = LocalDate.parse(fromDate, df);
        LocalDate newToDate = LocalDate.parse(toDate, df);
        return this.chequeService.getBetweenOrderByDateAsc(newFromDate, newToDate, pageable);
    }

    @GetMapping("/pending/sum")
    public double getPending() {
        return this.chequeService.sumPending();
    }

    @GetMapping("/paid/sum")
    public double getPaid() {
        return this.chequeService.sumPaid();
    }

    @GetMapping("/sum")
    public double getSum() {
        return this.chequeService.sum();
    }

    @GetMapping("/sum/pending/between")
    public double getSumPendingBetween(@RequestParam String fromDate, @RequestParam String toDate) {
        try {
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
            LocalDate newFromDate = LocalDate.parse(fromDate, df);
            LocalDate newToDate = LocalDate.parse(toDate, df);
            return this.chequeService.sumPendingBetween(newFromDate, newToDate);

        } catch (Exception e) {
            return 0;
        }
    }

    @GetMapping("/sum/paid/between")
    public double getSumPaidBetween(@RequestParam String fromDate, @RequestParam String toDate) {
        try {
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
            LocalDate newFromDate = LocalDate.parse(fromDate, df);
            LocalDate newToDate = LocalDate.parse(toDate, df);
            return this.chequeService.sumPAidBetween(newFromDate, newToDate);
        } catch (Exception e) {
            return 0;
        }

    }

    @GetMapping("/sum/all/between")
    public double getSumBetween(@RequestParam String fromDate, @RequestParam String toDate) {
        try {
            DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive()
                    .append(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toFormatter();
            LocalDate newFromDate = LocalDate.parse(fromDate, df);
            LocalDate newToDate = LocalDate.parse(toDate, df);
            return this.chequeService.sumBetween(newFromDate, newToDate);
        } catch (Exception e) {
            return 0;
        }

    }

    @PutMapping("/pay/{id}")
    public Cheque payCheque(@PathVariable(value = "id") Long id) {
        return this.chequeService.payCheque(id);
    }

    @GetMapping("")
    public Cheque getByNumber(@RequestParam(value = "number") int number) {
        return this.chequeService.getByNumber(number);
    }

    @GetMapping("/provider/{id}")
    public Page<Cheque> getAllByProvider(@PathVariable Long id, Pageable pageable) {
        return this.chequeService.getByProvider(id, pageable);
    }

    @GetMapping("/pending/date")
    public Page<Cheque> getAllPendingSortedByDate(Pageable pageable) {
        return this.chequeService.getByStateOrderByDateAsc(false, pageable);
    }

    @GetMapping("/paid/date")
    public Page<Cheque> getAllPaidSortedByDate(Pageable pageable) {
        return this.chequeService.getByStateOrderByDateAsc(true, pageable);
    }

}
