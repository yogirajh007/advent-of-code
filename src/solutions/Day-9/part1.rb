thefile = File.open('../../input/day-9')
$input = thefile.read.split
thefile.close

def check_above(i, j)
    if i > 0
        $input[i-1][j] > $input[i][j]
    else
        true
    end
end

def check_below(i, j)
    if i < 99
        $input[i+1][j] > $input[i][j]
    else
        true
    end
end

def check_left(i,j)
    if j > 0
        $input[i][j-1] > $input[i][j]
    else
        true
    end
end

def check_right(i,j)
    if j < 99
        $input[i][j+1] > $input[i][j]
    else
        true
    end
end



def check_lowest(i,j)
    [check_left(i,j), check_right(i,j), check_below(i,j), check_above(i,j)].all?
end

#puts $input.each{|line| puts line.include?("5")}

$sum = 0
for i in 0..99
    for j in 0..99
        if check_lowest(i,j)
            $sum = $sum + $input[i][j].to_i + 1
        end
    end
end

puts $sum
