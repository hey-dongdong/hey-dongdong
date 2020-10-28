package com.ewha.heydongdong.domain.datatype;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomOption {

    private Integer shotAmericano;
    private Integer shotLatte;
    private Boolean milk;
    private Boolean mint;
    private Boolean condensedMilk;
    private Boolean chocolate;
    private Boolean caramel;
    private Boolean soyMilk;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("CustomOption(");

        if (shotAmericano != null)
            sb.append("shotAmericano=" + shotAmericano + ", ");
        if (shotLatte != null)
            sb.append("shotLatte=" + shotLatte + ", ");
        if (milk != null)
            sb.append("milk=" + milk + ", ");
        if (mint != null)
            sb.append("mint=" + mint + ", ");
        if (condensedMilk != null)
            sb.append("condensedMilk=" + condensedMilk + ", ");
        if (chocolate != null)
            sb.append("chocolate=" + chocolate + ", ");
        if (caramel != null)
            sb.append("caramel=" + caramel + ", ");
        if (soyMilk != null)
            sb.append("soyMilk=" + soyMilk + ")");

        return sb.toString();
    }
}
