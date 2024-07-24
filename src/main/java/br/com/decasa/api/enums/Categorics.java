package br.com.decasa.api.enums;

public enum Categorics {
    ACESSORIOS_PARA_BANHEIRO("Acessórios para Banheiro"),
    CHUVEIROS_E_DUCHAS_DE_BANHO("Chuveiros e Duchas de Banho"),
    DUCHAS_HIGIENICAS("Duchas Higiênicas"),
    TORNEIRAS_LAVANDERIA_E_JARDINS("Torneiras, Lavandeira e Jardins"),
    TORNEIRAS_MISTURADORES_COZINHA("Torneiras/Misturadores Cozinha"),
    TORNEIRAS_MISTURADORES_LAVATORIO("Torneiras/Misturadores Lavatório"),
    MONOCOMANDOS("Monocomandos"),
    TORNEIRAS_COM_SENSOR("Torneiras com Sensor");

    private String categoric;

    Categorics(String categoric) {
        this.categoric = categoric;
    }

    public String getCategoric() {
        return categoric;
    }
}