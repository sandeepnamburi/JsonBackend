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
    public partial class Form3 : Form
    {
        List<int[]> list_of_p = new List<int[]>();
        public Form3(List<int[]> l1)
        {
            list_of_p = l1;
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            richTextBox1.Text = "";
            richTextBox2.Text = "";
            richTextBox3.Text = "";
            int player = 0;
            if (listBox2.GetItemText(listBox2.SelectedItem) == "Player1")
                player = 0;
            if (listBox2.GetItemText(listBox2.SelectedItem) == "Player2")
                player = 1;
            if (listBox2.GetItemText(listBox2.SelectedItem) == "Player3")
                player = 2;
            if (listBox2.GetItemText(listBox2.SelectedItem) == "Player4")
                player = 3;
            if (listBox2.GetItemText(listBox2.SelectedItem) == "Player5")
                player = 4;
            if (listBox2.GetItemText(listBox2.SelectedItem) == "Player6")
                player = 5;
            for (int i =0;i < 48;i ++)
            {
                int suit = i / 12;
                String suit_in_w = "";
                int card = i - (suit * 12) + 3;
                if (suit == 0)
                    suit_in_w = "spades";
                if (suit == 1)
                    suit_in_w = "clubs";
                if (suit == 2)
                    suit_in_w = "hearts";
                if (suit == 3)
                    suit_in_w = "diamonds";
                String to_print = card.ToString() + suit_in_w + " \n";
                if (list_of_p[player][i] == 0)
                    richTextBox1.Text += to_print;
                if (list_of_p[player][i] == 1)
                    richTextBox2.Text += to_print;
                if (list_of_p[player][i] == -1)
                    richTextBox3.Text += to_print;
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            int card = (Int32.Parse(textBox1.Text) - 3) + (Int32.Parse(textBox2.Text) - 1) * 12;
            richTextBox1.Text = "";
            richTextBox2.Text = "";
            richTextBox3.Text = "";
            for (int i =0; i < 6;i++)
            {
               
               
                    String output = "Player " + (i + 1).ToString() + " \n";
                if (list_of_p[i][card] == 0)
                    richTextBox1.Text += output;
                if (list_of_p[i][card] == 1)
                    richTextBox2.Text += output;
                if (list_of_p[i][card] == -1)
                    richTextBox3.Text += output;
            }
        }
    }
}
