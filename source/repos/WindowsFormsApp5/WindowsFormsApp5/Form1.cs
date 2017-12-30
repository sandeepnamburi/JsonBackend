using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace WindowsFormsApp5
{
    public partial class Form1 : Form
    {
        int[] p1 = new int[48];
        int[] p2 = new int[48];
        int[] p3 = new int[48];
        int[] p4 = new int[48];
        int[] p5 = new int[48];
        int[] p6 = new int[48];
        List<int[]> list_of_p = new List<int[]>();
        

        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            for(int i = 0; i < 48;i++)
            {
                p1[i] = 4;
                p2[i] = 4;
                p3[i] = 4;
                p4[i] = 4;
                p5[i] = 4;
                p6[i] = 4;

            }
            list_of_p.Add(p1);
            list_of_p.Add(p2);
            list_of_p.Add(p3);
            list_of_p.Add(p4);
            list_of_p.Add(p5);
            list_of_p.Add(p6);




        }

        private void button1_Click(object sender, EventArgs e)
        {
            int card = (Int32.Parse(textBox3.Text)-3) + (Int32.Parse(textBox5.Text)-1) * 12;
            
            int start = (Int32.Parse(textBox5.Text)-1) * 12;
            int end = start + 12;
            if (card - start <= 5)
                end = start + 6;
            else
                start = start + 6;
            int asker = Int32.Parse(textBox1.Text) -1;
            int second = Int32.Parse(textBox2.Text) - 1;
            list_of_p[asker][card] = -1;
            for (int i = card; i < end; i++)
            {
                if(list_of_p[asker][i] != -1 && list_of_p[asker][i] != 1)
                {
                    list_of_p[asker][i] = 0;
                }

             }
            for (int i = card; i >= start; i--)
            {
                if (list_of_p[asker][i] != -1 && list_of_p[asker][i] != 1)
                {
                    list_of_p[asker][i] = 0;
                }

            }
            if(textBox4.Text == "s")
            {
                list_of_p[asker][card] = 1;
                for(int i=0; i < 6;i++)
                {
                    if (i == asker)
                        continue;
                    else
                    {

                        list_of_p[i][card] = -1; 
                    }
                }
            }
            if(textBox4.Text == "f")
            {
                list_of_p[second][card] = -1;
            }

        }

        private void button2_Click(object sender, EventArgs e)
        {
            int card = (Int32.Parse(textBox6.Text)-3) + (Int32.Parse(textBox7.Text)-1) * 12;
            p1[card] = 1;
            for(int i=1;i < 6;i++)
            {
                list_of_p[i][card] = -1;
            }
            
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Form3 f3 = new Form3(list_of_p) ;
            f3.Show();
            
        }
    }
}
