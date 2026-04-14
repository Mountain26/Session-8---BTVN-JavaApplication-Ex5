package ra.edu.ex5.model.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import ra.edu.ex5.custom_validate.ValidTourCode;
import ra.edu.ex5.custom_validate.ValidTourDates;
import ra.edu.ex5.custom_validate.ValidTourPrices;

import java.time.LocalDate;

@ValidTourPrices
@ValidTourDates
public class TourDto {

    @ValidTourCode
    private String tourCode;

    @NotNull(message = "Người lớn bắt buộc phải có giá vé")
    @Positive(message = "Giá vé người lớn phải lớn hơn 0")
    private Long adultPrice;

    @NotNull(message = "Trẻ em bắt buộc phải có giá vé")
    @Positive(message = "Giá vé trẻ em phải lớn hơn 0")
    private Long childPrice;

    @NotNull(message = "Ngày khởi hành là bắt buộc")
    @FutureOrPresent(message = "Ngày khởi hành phải từ hôm nay trở đi")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc là bắt buộc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public TourDto() {
    }

    public String getTourCode() {
        return tourCode;
    }
    public void setTourCode(String tourCode) {
        this.tourCode = tourCode;
    }

    public Long getAdultPrice() {
        return adultPrice;
    }
    public void setAdultPrice(Long adultPrice) {
        this.adultPrice = adultPrice;
    }

    public Long getChildPrice() {
        return childPrice;
    }
    public void setChildPrice(Long childPrice) {
        this.childPrice = childPrice;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}

