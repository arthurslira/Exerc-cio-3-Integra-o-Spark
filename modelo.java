public class modelo {
    public class chuteira {
        private int codigo;
        private String marca;
        private String tamanho;
        private double preco;
        
        public chuteira() {
            this.codigo = -1;
            this.marca = "";
            this.tamanho = "";
            this.preco = -1;
        }
        
        public chuteira(int codigo, String marca, String tamanho, double preco) {
            this.codigo = codigo;
            this.marca =marca;
            this.tamanho = tamanho;
            this.preco = preco;
        }
    
        public int getCodigo() {
            return codigo;
        }
    
        public void setCodigo(int codigo) {
            this.codigo = codigo;
        }
    
        public String getmarca() {
            returnmarca;
        }
    
        public void setmarca(Stringmarca) {
            this.marca =marca;
        }
    
        public String getTamanho() {
            return tamanho;
        }
    
        public void setTamanho(String tamanho) {
            this.tamanho = tamanho;
        }
    
        public double getPreco() {
            return preco;
        }
    
        public void setPreco(double preco) {
            this.preco = preco;
        }
    
        @Override
        public String toString() {
            return "chuteira [codigo = " + codigo + ",marca = " +marca + ", tamanho = " + tamanho + ", preco = " + preco + "]";
        }
    }
}
